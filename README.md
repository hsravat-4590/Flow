# Flow

Flow is a Multiplatform Library which allows you to use a custom DSL to define a process flow

## Get Started

### Download Dependencies
TODO

### Create ProcessSteps
A `processStep` is a step of the flow and is used to represent a block of code. These functions are executed within the
`execute` method of any class which implements the interface

For example, suppose we wanted a class which would add two values together and then proceed to the next stage:

```kotlin
class AddStep: ProcessStep {
    
    val num1 = 1
    val num2 = 5
    lateinit var total: Int
    
    fun getId() = "add-step"
    
    fun execute(flowManager: FlowManager, bundle: Bundle): FlowAction {
        // Execute work here
        total = num1 + num2
        return FlowAction.CONTINUE
    }
}
```

Process steps can be more complex and you're free to pass in other information via constructors or through dependency injection.

### Add a StepInjector implementation

### Define your Flow

### Execute the Flow