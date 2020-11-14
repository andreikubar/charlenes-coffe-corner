Charlene's Coffee Corner
-------------------------

This is a console application.

### Build
Build using maven
```
mvn install
```

### Usage
Start by running in the command line
```
java com.company.Main
```

You will be presented with options for selecting items into the new order, e.g.:

```
Choose product:

1 Small Coffee at 2.50 CHF
2 Medium Coffee at 3.00 CHF
3 Large Coffee at 3.50 CHF
4 Bacon Roll at 4.50 CHF
5 Freshly squeezed orange juice at 3.95 CHF

Enter option: 
```

Should a product have available extras, you will be presented with a respective dialog, e.g.:
```
Add an extra? y/n
```
If 'y' is entered, you will be presented the list of available options for the given product, e.g.:
```
Choose extra:

1 Extra milk at 0.30 CHF
2 Foamed milk at 0.50 CHF
3 Special roast coffee at 0.90 CHF

Enter option: 
```

After a selection of products is added into the order, order can be completed, e.g.:
```
Complete order? y/n
```
After entering 'y', the final receipt is printed out showing the total price of the order, e.g.:
```
Order:
----------------------------------------
Large Coffee                   3.50 CHF
Bacon Roll                     4.50 CHF
----------------------------------------
Total                          8.00 CHF
```

#### Bonuses
##### Snack + Beverage bonus
When ordering Snack accompanied by a beverage, customer gets an extra for free. 
When adding a product into selection, the program will suggest adding a product of the other category into selection, e.g.:
```
Add a matching combo? y/n
```
If 'y' is answered, you will be presented a list of matching combo products, e.g.:
```
Choose a matching product:

1 Bacon Roll at 4.50 CHF

Enter option: 
```
If there are beverage with an extra and a snack selected together, the extra will get the price of 0 CHF, e.g.:
```
Order:
----------------------------------------
Small Coffee                   2.50 CHF
Extra milk                     0.00 CHF
Bacon Roll                     4.50 CHF
```

##### Stamp card bonus
If a customer presents a stamp card with 5 stamps, they get a beverage for free.
This bonus can be applied at the end, when the order is being finished. E.g.:
 ```
Apply 5th beverage free bonus? y/n
```
If 'y' is entered, the price of a beverage becomes 0 CHF, e.g.:
```
Order:
----------------------------------------
Small Coffee                   0.00 CHF
Bacon Roll                     4.50 CHF
```

