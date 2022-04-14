Problem 1: The Birthday Presents Party
    Compile:
    javac Presents.java Servant.java PresentChain.java PresentNode.java
    Run:
    java Presents
    Solution Summary:
    Presents.java acts as the Minotaur and starts by adding presentCount number of Presents represented as unique integers to a BlockingQueue representing the bag.
    Then the Presents.java spins up four Servant.java threads.
    While the Servants are running the Minotaur/Presents.java thread will occasionally request they check if certain gifts are present in the chain.
    The Servant threads check if the Minotaur has requested a check for a gift in the chain, otherwise they randomly add gifts to the chain from the "bag" and remove them from the chain.
    The Servants are more likely to add than remove in order to boost performance by maintaining a larger chain giving more options to remove without contention.
    The PresentChain is a linked list with add, remove, and contains operations.
    The add operation uses a fine-grained hand-over-hand approach.
    The remove operation randomly and optimistically iterates through the list and picks a present to remove.
    The PresentNode is what the PresentChain is made of and simply stores the id, an AtomicBoolean locked and TTAS locked and unlock methods.

    The solution that the Minotaur and his servants previously used that resulted in more presents than thank you notes likely messed up when concurrently adding presents to the chain.
    For instance, if you had the following chain 1,5,7 and you tried to add 3 and 4 at the same time you could end up overwriting 1's next if you weren't careful resulting in no valid way to get to the overwritten number.

Problem 2: Atmospheric Temperature Reading Module
    Compile:
    javac TemperatureModule.java Sensor.java
    Run:
    java TemperatureModule
    Solution Summary:
    TemperatureModule.java spins up the 8 sensor threads which write into the temps array at their designated intervals e.g. the 3rd sensor writes on multiples of 3 in the array.
    The module gives the sensors a headstart before it beings analyzing the data in sync with the sensor readings (but far enough behind to calculate max temperature differences for intervals of 10).
    The module then prints out the results and repeats for each report.