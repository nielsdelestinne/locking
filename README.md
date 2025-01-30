# Locking

> A simple project to experiment with pessimistic locking and inspect the locking situation in both the application on thread level, as in the database.

## Usage

1. Start the `db` via docker compose (see `/docker`).
2. Start the `LockingApplication`
3. Start the client `LockingClientApplication`
4. Check the output of `LockingApplication`

## Some experimenting...

When concurrent threads access different users, there's no locking. This is to be expected.

```
1 | 2025-01-30T21:12:44.722587 - Start in thread: http-nio-8088-exec-1
2 | 2025-01-30T21:12:44.722592 - Start in thread: http-nio-8088-exec-2
4 | 2025-01-30T21:12:44.722601 - Start in thread: http-nio-8088-exec-4
3 | 2025-01-30T21:12:44.722592 - Start in thread: http-nio-8088-exec-3
3 | 2025-01-30T21:12:44.744375 - Continued in thread: http-nio-8088-exec-3
2 | 2025-01-30T21:12:44.744378 - Continued in thread: http-nio-8088-exec-2
4 | 2025-01-30T21:12:44.744375 - Continued in thread: http-nio-8088-exec-4
1 | 2025-01-30T21:12:44.744386 - Continued in thread: http-nio-8088-exec-1
```

When concurrent threads access the same user, there's locking. Only after the transaction ends is the lock released and can the next thread in line continue.

```
2025-01-30T21:49:36.616321 - Start 				 thread: http-nio-8088-exec-3
2025-01-30T21:49:36.616321 - Start 				 thread: http-nio-8088-exec-1
2025-01-30T21:49:36.616346 - Start 				 thread: http-nio-8088-exec-2
2025-01-30T21:49:36.616332 - Start 				 thread: http-nio-8088-exec-4
2025-01-30T21:49:36.654566 - Continued 			 thread: http-nio-8088-exec-3
2025-01-30T21:49:38.655744 - Ended 				 thread: http-nio-8088-exec-3
2025-01-30T21:49:38.667017 - Continued 			 thread: http-nio-8088-exec-4
2025-01-30T21:49:40.672357 - Change detected 	 thread: http-nio-8088-exec-4
2025-01-30T21:49:40.679494 - Ended 				 thread: http-nio-8088-exec-4
2025-01-30T21:49:40.692868 - Continued 			 thread: http-nio-8088-exec-2
2025-01-30T21:49:42.698096 - Change detected 	 thread: http-nio-8088-exec-2
2025-01-30T21:49:42.699010 - Ended 				 thread: http-nio-8088-exec-2
2025-01-30T21:49:42.703714 - Continued 			 thread: http-nio-8088-exec-1
2025-01-30T21:49:44.707802 - Change detected 	 thread: http-nio-8088-exec-1
2025-01-30T21:49:44.708711 - Ended 				 thread: http-nio-8088-exec-1
```

Query in DB for lock inspection:
```
SELECT pg_locks.pid,
       locktype,
       relation::regclass AS relation,
       mode,
       granted,
       transactionid,
       virtualtransaction,
       virtualxid,
       fastpath,
       wait_event
FROM pg_locks
         LEFT JOIN pg_stat_activity ON pg_locks.pid = pg_stat_activity.pid
WHERE locktype = 'transactionid'
ORDER BY granted DESC, relation;

```
