# Flow
[![Build Gradle project](https://github.com/hsravat-4590/Flow/actions/workflows/build.yml/badge.svg)](https://github.com/hsravat-4590/Flow/actions/workflows/build.yml)

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

Process steps can be more complex, and you're free to pass in other information via constructors or through dependency injection.

### Add a StepInjector implementation
The step injector is a simplified approach to dependency injection which allows you to inject step classes into a flow. 
There are several advantages to this approach against providing an instance at the time of defining the flow mainly that you can use existing DI code.

To add a custom implementation, you need to implement the `StepInjector` interface

### Define your Flow

The Flow DSL makes it easy to define your flow. For example:
```kotlin
fun getFlow(): Flow {
    return flow("DownloadFileFlow") {
        startingStep = "getMetadata"
        bundle {
            // The bundle can store some info and can be edited by each step in the process
            add("UserName", "user1")
            add("password", "Pa55word")
        }
        step("getMetadata", GetMetadata::class) {
            actionHandler {
                handler(FlowAction.CONTINUE) {
                    nextStep = "downloadFile"
                }
                handler(FlowAction.ERROR) {
                    nextStep = "metadataError"
                }
            }
        }
        step("downloadFile", DownloadFile::class) {
            actionHandler {
                handler(FlowAction.EXIT) {
                    // End of Flow
                }
            }
        }
        ...
    }
}
```

### Execute the Flow

Flows are executed and controlled by a `FlowManager`. There is a default implementation (`FlowManagerImpl`) which works but you may use your own implementation for advanced use cases

To execute a flow:

```kotlin
class Runtime(val flowManager: FlowManager) {
    
    val flowName = "testFlow"
    
    fun runFlow() {
        flowManager.startFlow(testFlow)
    }
}
```
