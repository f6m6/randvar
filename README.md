randvar
=======

discrete, Double-valued random variables with operators and companion object in Scala

try examples below in REPL with `:load RandVar.scala`, `import RandVar.RV`

* `val die = new RV(6)` for a six-sided fair die
* use `die.get` to simulate a single throw of the die
* `val dice = die + die` - `+ - * /` operators assume independence, sum of two independent die rolls
* can also use unary `-` to negate support values

* `X = RV((0.1, 13.0), (0.9, -666.0))` - using `RV` companion object to input a variable-length distribution
* `X.mean`, `X.sd`, `X.variance`, `X.moment(3)` work as mathematically defined

* can access `dist`, `support`, `ps` and `n` fields

