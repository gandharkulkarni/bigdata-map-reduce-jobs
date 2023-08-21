# Logs, Passwords, and MapReduce Jobs

## Discussion

**url-dataset-1m (MR)**

    Total time spent by all maps in occupied slots (ms)=55264
    Total time spent by all reduces in occupied slots (ms)=61504
    Total time spent by all map tasks (ms)=6908
    Total time spent by all reduce tasks (ms)=3844

**url-dataset-10m (MR)**

    Total time spent by all maps in occupied slots (ms)=810360
    Total time spent by all reduces in occupied slots (ms)=242144
    Total time spent by all map tasks (ms)=101295
    Total time spent by all reduce tasks (ms)=15134

**url-dataset-100m (MR)**

    Total time spent by all maps in occupied slots (ms)=28219952
    Total time spent by all reduces in occupied slots (ms)=4300048
    Total time spent by all map tasks (ms)=3527494
    Total time spent by all reduce tasks (ms)=268753

**Lab 1 : Single threaded golang program**

    url-dataset-1m : 1.88s
    url-dataset-10m : 4.99s
    url-dataset-100m : 2.86s (without buffer size fix)
    url-dataset-100m : 295.334754s (with buffer fix)
    
Lab1 program worked better with small, medium and large files sized files (clearly I am doing something wrong here :| ).

**PwndPasswordAnalyzer**

    Total time spent by all maps in occupied slots (ms)=20354016
    Total time spent by all reduces in occupied slots (ms)=17946864
    Total time spent by all map tasks (ms)=2544252
    Total time spent by all reduce tasks (ms)=1121679

Findings    

    helloworld password appeards in the database    

Output file : 

    Password: helloworld is compromised. Breach Found:      25337

Bank Password : 5_%_extra_credit_for_this

Bank password does not appear in the database (pheww!!)

**UFO Sighting dataset**

This dataset contains 80,000+ reports of UFO sightings over the last century. It contains column fields such as datetime, city, state, country, shape, comments etc.

https://www.kaggle.com/datasets/NUFORC/ufo-sightings

Our Map reduce job analyzes the dataset and finds top 10 locations with UFO sightings.

    seattle, wa, us 568
    phoenix, az, us 484
    las vegas, nv, us       389
    los angeles, ca, us     372
    san diego, ca, us       358
    portland, or, us        351
    houston, tx, us 313
    chicago, il, us 294
    tucson, az, us  258
    miami, fl, us   247
