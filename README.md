## The Brewery problem

You run a Brewery, and there are a few different types of Beers you can prepare for customers. Each Beer can be either "Classic" or "Barrel Aged".
You have a number of customers, and each has some Beers that they like, either classic or barrel aged. No customer will like more than one Barrel Aged Beer. You want to mix the Beers, so that:
There is just one batch for each type of Beers, and it's either Classic or Barrel Aged. For each customer, there is at least one Beer they like. You make as few Barrel Aged Beers as possible (because they are more expensive).
Your app should download an input file, and print a result on screen.
An example input file is:

```
5
1 B 3 C 5 C
2 C 3 B 4 C
5 B
```

The first line specifies how many Beers types there are (5 in this case). Each subsequent line describes a customer.
For example, the first customer likes Beer 1 Barrel Aged, Beer 3 Classic and Beer 5 Classic.
Your program should read an input string like this, and print out either that it is impossible to satisfy all the customers, or describe, for each of the Beers, whether it should be made Classic or Barrel Aged.
The output for the above file should be a list of beer cells with the following labels

````
[1->Classic; 2->Classic; 3->Classic; 4->Classic;5->Barrel]
Or in short form
[C C C C B]
````

...because all customers can be made happy by every Beer being prepared as Classic except number 5.
An example of a file with no solution is:

```
1
1 C
1 B
```

Your app should print: ​No solution exists

A slightly richer example is:

```
5
2 B
5 C
1 C
5 C 1 C 4 B
3 C
5 C
3 C 5 C 1 C
3 C
2 B
5 C 1 C
2 B
5 C
4 B
5 C 4 B
```

...which should show:

```
C B C B C
```

One more example. The input:

```
2
1 C 2 B
1 B
```

...should produce

```
B B
```

## Result screen
The result screen contains a list of beer cells with the following information:
- Image
- Name
- ABV
- Either is Classic or Barrel Aged
Note: The id to use for retrieving the beer info from the API is the Beer Type index, usually [1,2,3,4,..., # of types]
When tapping on a beer cell a Beer Details Screen is presented.

## Beer Details screen
The beer screen is a guide for the brewer and contains:
● Image
● Name
● ABV
● Description

The beer list can be accessed through the PUNK API: ​https://punkapi.com/documentation/v2