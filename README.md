# A Star Project
## Dunbar Paul Birnie IV
---
### Part 0:
All 50 101x101 GridWorlds are stored as serialized objects in the file 'fiftyGridWorlds.ser'. The provided python script (showGrid.py) is used to visualize the given GridWorlds. The MapLoader main method will call this script to visualize a given map.

Example:
<p align="center">
<img src="/home/dubar/Pictures/pics/aigame1.jpg">

Above the blue square is the starting state, the red square is the target state, and the green represents the shortest path.

### Part 1:
a) Explain in your report why the first move of the agent for the example search problem from Figure 8 is to the east rather than the north given that the agent does not know initially which cells are blocked.


<p align="center">
<img src="/home/dubar/Pictures/pics/aigameexample2.png">

**Soln.** Given the agent does not already know the cells to the east are blocked it is natural fo rthe A* algortihm to make the first action to explore the cell to the east. This is seen when we look at the cost values associated with each neighbor cell. On the first iteration the cells E1, D2, and E3 are examined. The costs are as follows:
E1: g = 1, h = 4, f = 5
D2: g = 1, h = 4, f = 5
E3: g = 1, h = 2, f = 3
The A* algorithm prioritizes exploring the cells with the lowest cost first so the first cell explored will be E3. 

b) The A* algorithm will find the target or discover the traversal is impossible in finite time due to the nature of the algotithm and its use of open and closed cell sets. The finite upper bound is directly correlated to the number of unblocked cells. This is due to the way the algoithm explores cells. When a cell is explored it is added to the open set and then later processed. Once processed, it is added to the closed set. Cells in the closed set will not be expanded again. Because each action can only move from unblocked cell to adjacent unblocked cell, the algorithm will be bounded by the number of unblocked cells. When all unblocked cells have been expanded the algorithm will be at the target and if not, than the traversal is necessarily impossible.

### Part 2:
On a 101 x 101 GridWorld with ties broken prioritizing lower g value cells the runtime for my code was on average 59 ms. However, when I ran it such that it prioritized the higher g value the runtime was on average 3 ms. I suspect that the reason for this large discrepancy is because under the situation of multiple cells having the same f value, choosing cells with smaller g-value returns back towards cells near the start point whereas choosing cells with larger g-value explore cells near the goal point.
### Part 3:
I found that on average the two algorithms performed equivalently on near random maps. Over 50 different maps both algortithms averaged out to 2 ms. However I believe that this question is highly dependent on the topology of the map. For example if there was a large obstruction near the target state and none by the start state, than backward would work best as it would need to explore fewer cells to get around the obstruction than the forward version would. In the maps I generated the blockages were very uniformly distributed such that there was no clear advantage on average for the two cases.
### Part 4:
The heuristic of manhattan distance is a good heuristic as in an unblocked map the fastest path is that which follows horizontally the entire way and then verticaly to the level of the target. There can be no path that is shorter than the manhattan distance, only paths which must be longer due to blockages. To prove this is an optimal heuristic let us assume the opposite:
$$
\exists(n, a, m) : h(n) \geq c(n, a, m) + h(m)
$$
moving $h(m)$ to the other side we see that:
$$
\exists(n, a, m) : h(n) - h(m) \geq c(n, a, m)
$$
since $h(m)$ and $h(n)$ are manhattan distances from n to m:
$$
h(n,m) : |h(n) - h(m)|
$$
We can combine these equations to get:
$$
\exists(n, a, m) : |h(n) - h(m)| \geq c(n, a, m)
$$
We can think of $h(n, m)$ as sides of a square where one side is $|x(n)- x(m)|$ and another side is $|y(n) - y(m)|$. The only way for $h(n, m) \geq c(n, a, m)$ to be true is that $c(n, a, m)$ is the distance of moving through diagonals of this square. However, since only four directions are allowed in grid worlds, this c(n, a, m) does not exist. Therefore, Manhattan Distance is indeed a consistant heuristics in gridworlds.
### Part 5:
Forward A* was much slower when compared to Adaptive A*. However, if we only ran Adaptive one time on a given map the results were on the same order of runtime. This is due to the fact that Adaptive A* will update the h values for the next iteration of the search. If only ran one time than there is no search utilizing the new, updated, h values. Forward A* is very consistent from search to search as it does not update h values at all. On the first iteration of both I found that the runtime was on average 2 ms. When I repeated the Adaptive A* algotihm on the same map this runtime decreased significantly. The best illustration of this is when ran consecutively from teh same starting point. In this case I got the runtime down to 1 ms for Adaptive A*. 
### Part 6:
In my implementation each cell stores the following values:

<p align="center">
<img src="/home/dubar/Pictures/pics/varsai3.png">

Obviously this is overkill, but for the small gridworlds i worked with this was the best way for me to implement the algorithms and learn from the implementation. If I was to port this imlementation to a game design I would need to reduce the data size of each cell. I could do this by not storing the Random object, and by storing the previous tree pointer as a two bit value specifying direction (eg. 00 as North, 01 as East and so forth). If I removed the Random object reference (8 bytes), and the prev pointer (8 bytes) I could significantly reduce the memory size. On a gridworld of 1001x1001 The total memory size would be:
$$
1001*1001*(64+64+1+2+2+64+64+64) = 325650325
$$
This would be a total size of approximately 40 GB of information. Since each cell is approximately 40 bytes the maximum size of gridworld that we coudl store in 4 MBytes is 10x10.
