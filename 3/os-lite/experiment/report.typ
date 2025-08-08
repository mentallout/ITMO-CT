= Лабораторная работа 5. Отчет


== Текущая конфигурация ОС

_`cat /proc/meminfo MemTotal`_
- Общий объем оперативной памяти: 4010076 kB
_`cat /proc/swaps Size`_
- Объем раздела подкачки: 4194300 kB
_`getconf PAGE_SIZE`_
- Размер страницы виртуальной памяти: 4096 B
_`cat /proc/meminfo MemFree`_
- Объем свободной физической памяти в ненагруженной системе: 482520 kB
_`cat /proc/meminfo SwapFree`_
- Объем свободного пространства в разделе подкачки в ненагруженной системе: 482520 kB


== 1 эксперимент

=== Подготовительный этап

Создан скрипт mem.bash

=== Наблюдения после проведения 1 этапа

_Примечание: когда начиналось освобождение ресурсов после аварийной остановки, консоль замирала ( в отчете заморозка и подобное)_

- Значения параметров памяти системы (верхние две строки над основной таблицей): 

_MiB Mem_: свободная память (free) уменьшалась после запуска скрипта, когда она опустилась ниже 200, активировался Swap. Значение buff/cache так же уменьшалось, пока не использовался Swap

_MiB Swap_: примерно через 20 секунд после запуска скрипта free память стала уменьшаться, как и avail Mem. Когда осталось 0.2 free консоль с top зависла

- Значения параметров в строке таблицы, соответствующей работающему скрипту:

_VIRT_: менялась на ~150000 каждые 5 секунд, остановилась на 6387740

_RES_: на 1.5g включился Swap, на 3.2g процесс остановился

_SHR_: 3200 -> 896

_%CPU_: почти все время 90-99, за секунд 5 до зависания консоли с top упало до 44

_%MEM_: постепенно росле с 8 до 83

_TIME+_: остановилось на 1:58.14

- Изменения в верхних пяти процессах (как меняется состав и позиции этих процессов):

_Самое начало_:

`PID USER COMMAND
3997 valeria mem.bash
1870 valeria gnome-shell
3238 valeria Isolate+
3634 valeria gnome-terminal
2369 valeria gjs`

_Позже поднималась `top`_

_В момент переключения на Swap_:

`PID USER COMMAND
3997 valeria mem.bash
54 root kswapd0
3238 valeria Isolate+
273 root kworker+
1870 valeria gnome-shell`

_По %CPU лидировали mem.bash и kswapd0_

_В момент заморозки консоли ( при kill pid3997)_:

`PID USER COMMAND
54 root kswapd0
3997 valeria mem.bash
3238 valeria Isolate+
14 root kworker+
28 root kworker+`

- Последние две записи о скрипте в системном журнале:

_`valeria@valeria:~/os-lite/experiment-valeriaaks/scripts$ sudo dmesg | grep "mem.bash"
...
[  790.761749] oom-kill:constraint=CONSTRAINT_NONE,nodemask=(null),cpuset=/,mems_allowed=0,global_oom,task_memcg=/user.slice/user-1000.slice/user@1000.service/app.slice/app-org.gnome.Terminal.slice/vte-spawn-797d38b0-e73c-4065-99c4-6c4be06ccf62.scope,task=mem.bash,pid=3997,uid=1000
[  790.761764] Out of memory: Killed process 3997 (mem.bash) total-vm:6416648kB, anon-rss:3353216kB, file-rss:0kB, shmem-rss:0kB, UID:1000 pgtables:12584kB oom_score_adj:200
`_

- Значение в последней строке файла report.log:

_`Step: 18000000, Array size: 90000000`_


=== Наблюдения после проведения 2 этапа

- Значения параметров памяти системы (верхние две строки над основной таблицей): 

_MiB Mem_: свободная память (free) уменьшалась после запуска скрипта, когда она опустилась до 0, активировался Swap, после этого выросла до ~100. Значение buff/cache уменьшалось с 1000+ до 40. После kill mem.bash free было ~1500, далее работа аналогично 1 этапу

_MiB Swap_: примерно через 20 секунд после запуска скрипта free память стала уменьшаться, как и avail Mem. Через минуту 0 free- консоль с top зависла. После kill mem.bash free было ~1800, далее работа аналогично 1 этапу

- Значения параметров в строках таблицы, соответствующей работающим скриптам ( 1) работают оба процесса 2) работает только mem2.bash): 

_VIRT_: 1) примерно одинаковые значения у обоих процессов: менялась на ~130000 каждые 3 секунды, остановилась на ~3627488 2) менялась на ~83000 каждые 3 секунды, остановилась на ~7247456

_RES_: 1) одинаковые значения у обоих процессов: на 1.2g включился Swap, на 1.7g процессы остановились 2) на 1.9g включился Swap, на 3.3g процесс остановился

_SHR_: 1) mem.bash 3200 -> 640 mem2.bash 3200 -> 1400 2) 1700 -> 1100

_%CPU_: 1) почти все время 90-99, за секунд 5 до зависания консоли с top упало до ~50 у обоих процессов 2) в пределах 80-100

_%MEM_: 1) у обоих процессов в районе 30-50 2) 50-90

_TIME+_: 1) остановилось на 1:05 2) остановилось на 2:38

- Изменения в верхних пяти процессах (как меняется состав и позиции этих процессов):

_Самое начало_:

`PID USER COMMAND
6814 valeria mem2.bash
6813 valeria mem.bash
1870 valeria gnome-shell
6739 valeria gnome-terminal
249 root systemd+`

_Позже поднимался `kthreadd`_

_В момент переключения на Swap_:

`PID USER COMMAND
6814 valeria mem2.bash
54 root kswapd0
6813 valeria mem.bash
6811 valeria top
17 root rcu_pre+`

_По %CPU лидировали mem.bash, mem.bash2 и kswapd0_

_В момент заморозки консоли ( при kill pid6813)_:

`PID USER COMMAND
54 root kswapd0
6813 valeria mem.bash
6814 valeria mem2.bash
514 root kworker/1:2H-kblockd
1870 valeria gnome-shell`

_При разморозке консоли, когда остался работать только один из скриптов_:

`PID USER COMMAND
6814 valeria mem2.bash
6739 valeria gnome-shell
89 root kworker/0:1H-kblockd
6739 valeria gnome-terminal
6581 valeria gjs`

_Работа аналогично 1 этапу_

_В момент заморозки консоли ( при kill pid6814)_:

`PID USER COMMAND
6814 valeria mem2.bash
54 root kswapd0
89 root kworker/0:1H-kblockd
1870 valeria gnome-shell
389 systemd+ systemd-oomd`

- Последние две записи о скриптах в системном журнале:

_`valeria@valeria:~/os-lite/experiment-valeriaaks/scripts$ sudo dmesg | grep "mem.bash"
...
[ 3972.603599] oom-kill:constraint=CONSTRAINT_NONE,nodemask=(null),cpuset=/,mems_allowed=0,global_oom,task_memcg=/user.slice/user-1000.slice/user@1000.service/app.slice/app-org.gnome.Terminal.slice/vte-spawn-d7e352e5-e6d7-43e1-a8b9-c8a7efde73cb.scope,task=mem.bash,pid=6813,uid=1000
[ 3972.603621] Out of memory: Killed process 6813 (mem.bash) total-vm:3651116kB, anon-rss:1815040kB, file-rss:128kB, shmem-rss:0kB, UID:1000 pgtables:7172kB oom_score_adj:200

valeria@valeria:~/os-lite/experiment-valeriaaks/scripts$ sudo dmesg | grep "mem2.bash"
...
[ 4158.551883] oom-kill:constraint=CONSTRAINT_NONE,nodemask=(null),cpuset=/,mems_allowed=0,global_oom,task_memcg=/user.slice/user-1000.slice/user@1000.service/app.slice/app-org.gnome.Terminal.slice/vte-spawn-d7e352e5-e6d7-43e1-a8b9-c8a7efde73cb.scope,task=mem2.bash,pid=6814,uid=1000
[ 4158.551906] Out of memory: Killed process 6814 (mem2.bash) total-vm:7273196kB, anon-rss:3619200kB, file-rss:128kB, shmem-rss:0kB, UID:1000 pgtables:14256kB oom_score_adj:200
`_

- Значение в последней строке файлов report2_1.log и report2.log:

_`Step: 800000, Array size: 4000000`_

_`Step: 16000000, Array size: 80000000`_

=== Обработка результатов

#underline[1) Динамика изменения параметров:]

_Потребление памяти_: увеличение размера массива ведет к увеличению потребления виртуальной памяти (VIRT) и реальной памяти (RES). Когда почти вся виртуальная память заканчивается используется Swap

_%CPU_: процессор загружается из-за добавления по 5 элементов в массив

_Swap_: при заполнении оперативной памяти система начинает выгружать данные в Swap. Если Swap заполнен, OOM-killer завершает процесс

#underline[2) Пороговые величины:]

_Размер массива в момент аварийной остановки_: определяет предельный объем памяти, доступный для процесса в системе. На 2 этапе два скрипта конкурируют за память, что ускоряет заполнение оперативной памяти и Swap. mem2.bash потреблял ресурсов в 2 раза больше, чем mem.bash ( исходя из записей в сис. журнале)

#underline[3) График:]

На графике показано изменение распределения virt и когда в работу входил swap

#image("graph.png")

== 2 эксперимент

=== Подготовительный этап

Создан скрипт newmem.bash

=== Наблюдения после проведения эксперимента

С фиксированным N в 10 раз меньшим размера массива при аварийной остановке ( 1 этап 2 эксперимента), наблюдались аварийные остановки в связи с увечилением конкуренции за ресурсы процессами, что привело к их нехватке и сложности с распределением.

При N = 900000 и К = 30 аварийных остановок не было.
