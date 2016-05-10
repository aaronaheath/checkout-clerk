# checkout-clerk
As a checkout clerk, I want a service API that scans a series of items and calculates the total price, accounting for the items single unit price or it's volume price.

The volume price is based on a fixed set of units. For example, an individual price for an item could be $1 per unit, but $5 for a set of 6 units. If the customer buys 7 units, then they should be charged $5 for the set of 6, and $1 for the additional unit, making a total of $6 (think of buying a six-pack of soda and an extra individual can).

For the purposes of this execise, there will be no sales tax.
ACCEPTANCE CRITERIA

 At a minumum, the service should implement the following pseudo code:

    service.setPricing([DATA])
    service.scan(code)
    service.printReceipt()

The output should be a 'reciept' data structure listing the items (aggregated by product) annd the total amount due.
TESTS

The service needs to demonstrate that it passes the following tests, using these prices:

PRODUCT CODE    UNIT PRICE    VOLUME PRICE
------------    ----------    ------------
A               $2            4 for $7
B               $12
C               $1.25         6 for $6
D               $0.15
  

TEST 1: Items ABCDABAA Total $32.40
TEST 2: Items CCCCCCC Total $7.25
TEST 3: Items ABCD Total $15.40

## Implementation

__CheckoutService__
To help keep the data & state sharing to a minimum I changed the scan mentod from a void to retuning a Product object. Print Receipt was changed from a char to a String - this was done to simplify the testing of the method.