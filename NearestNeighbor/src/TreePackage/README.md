# NearestNeighbor
## Background
The purpose of this project was to utilize the power of a binary search tree in combination with a point class containing x and y
coordinates to determine the nearest neighbor to another point. This was a challenging task as it required finding a way to sort
a BST when there are two variables that can be used as criteria per node, traversing this tree in an efficent manner, adding new
points in their proper sorted location, and finally writing the algorithm that would determine the nearest neighbor to a certain 
point. 
## Alternate methods 
Some may suggest that it is possible to achieve a similar result to this project in a much simplier manner 
by calculating the distance between the point the user entered and all other points, selecting the lowest distance, and 
locating the nearest neighbor. However, the former method suggested is grossly inefficent in terms of Big O. While this 
project's implementation may be more complicated, it is able to locate the nearest point in O(logn) complexity due to the nature 
of a binary search tree. In comparison, the brute force method of calculating the nearest point by calculating all the distances
and selecting the minimum has a time complexity of O(n). 
### Credit
This project was created as an assignment for Dr. Jacob Schrum's Computer Science II course
