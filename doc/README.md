# Pushdown Automata Simulator

Pushdown automata simulator coded in Java by Jose Hernandez.

## Packages overview

* __gui__ contains the class(es) related to the Graphical User Interface.
* __util__ contains the non-problem-specific algorithms and data structures.

* __foobar__ contains the problem-specific algorithms.
* __foobar.structs__ contains the problem-specific data structures.

## Source explanation

* _MainWindow_ fires up the GUI.
* When 'Load' is chosen, _FoobarIO_ converts a file into a _FoobarData_.
	* Which itself is used to create a _Foobar_.
* When 'Run' is chosen, the already created _Foobar_ executes a trace of the given string.