# JavaAsyncObject

AsyncObject for Java with an usage demonstration.

Developed using the [JetBrains IntelliJ IDEA] (https://www.jetbrains.com/idea/) IDE.

## How it works this object

This object can run actions in background using threads. Can also be executed with an Executor (ThreadPoolExecutor, ExecutorService). It uses 4 tasks (Functional Interfaces):
  
1. Action (required)
2. Success (optional)
3. Error (optional)
4. Done (optional)

This object will run the `action()` method in a new thread. Once the `Action` is finished, it will give feedback through the `done()` method. If we want to get track of the response we will use the `Success` method. And if we want to get track of the possible errors with the `Action` we will use the `Error` method. Except the `Action` method, all are optional.

__Examples__:

```java
new AsyncObject<Void>()
                .action(() -> {
                    System.out.println("AsyncObject");
                    return null;
                })
                .run();
```

```java
new AsyncObject<String>()
                .action(() -> "AsyncObject")
                .done(() -> System.out.println("request done"))
                .subscribe(
                        System.out::println,
                        e -> System.out.println("request error: " + e.toString())
                );
```

## The example

In this example, we will use 7 different `AsyncObjects`, each one with a different delay for the response simulating background tasks. All the calls are done inside the method `start()`.

The details for each requests are:

1. It uses the `Action`, `Done` functions, has 2 seconds delay and returns an error. 
2. It uses the `Action`, `Done` functions, the `subscribe` method (`Success`, `Error`) and has 4 seconds delay.
3. It uses the `Action`, `Done` functions, the `subscribe` method (`Success`, `Error`), has 3 seconds delay (it's executed in a `ThreadPool`).
4. It uses the `Action` function, has 5 seconds delay (it's executed in a `ThreadPool`).
5. It uses the `Action` function, the `subscribe` method (`Success`, `Error`), has 7 seconds delay and returns an error (it's executed in a `ThreadPool`).
6. It uses the `Action`, `Done` functions, the `subscribe` method (`Success`), has 6 seconds delay and executes `Action` 7 when gets the response.
7. It uses the `Action`, `Done` functions, the `subscribe` method (`Success`).

__Note__: The result will be something like this (notice that the `-> END PROGRAM` and the `request x started` lines maybe sorted different because of the threads):

```
-> START PROGRAM
request 1 started in [async-object-thread-11] (2 seconds delay response)
request 2 started in [async-object-thread-12] (4 seconds delay response)
request 3 started in [pool-1-thread-1] (3 seconds delay response)
request 4 started in [pool-1-thread-2] and won't have response feedback (5 seconds process)
request 5 started in [pool-1-thread-3] (4 seconds delay response)
-> END PROGRAM
request 6 started in [async-object-thread-16] (4 seconds delay response)
java.lang.Exception: Fake error
request 1 done
request 3 response: {user_id="15", user_name="Thelma"} in [pool-1-thread-1]
request 3 done
request 2 response: {user_id="31", user_name="Marcus"} in [async-object-thread-12]
request 2 done
Action successfully completed
request 6 done
request 7 started in [async-object-thread-18]
request 7 response (Frank == Michael): false
request 7 done
request 5 error in [pool-1-thread-3]
```

## License
    Copyright 2015 Esteban Latre
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
        http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
