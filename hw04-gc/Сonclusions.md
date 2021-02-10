Запуск выполнялся на хипе:

-Xms4096m -Xmx4096m -XX:+UseG1GC

-Xms4096m -Xmx4096m -XX:+UseParallelGC

Результаты запусков в таблице

UseParallelGC 
Final cycle: 4731. CountRunGc: 7. TotalTime: 9499.
6m34sec
11 col 20,696
Final cycle: 4731. CountRunGc: 6. TotalTime: 8127.
Final cycle: 4731. CountRunGc: 4. TotalTime: 9268.
4 min 42 sec
10 20,0024


UseG1GC
Final cycle: 7096. CountRunGc: 31. TotalTime: 3807.
35 min 25 sec
Final cycle: 7096. CountRunGc: 32. TotalTime: 7739.
28 min 41 sec
35 totaltime 17.498


UseConcMarkSweepGC
TimeTo Outofmemory
Final cycle: 4731. CountRunGc: 21. TotalTime: 90784.
4 min 29 sec

