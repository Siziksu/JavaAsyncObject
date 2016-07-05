# JavaAsyncObject
AsyncObject for Java with an usage demonstration.

Developed using the [JetBrains IntelliJ IDEA] (https://www.jetbrains.com/idea/) IDE.

## How it works this object

This object can run actions in background using threads. Can also be executed with an Executor (ThreadPoolExecutor, ExecutorService). It uses 3 tasks (interfaces):
  
1. Action
2. Success
3. Error

The simplest way to call this object is:

```java
new AsyncObject<String>()
    .action(new AsyncObject.Action<String>() {
    
        @Override
        public String action() throws Exception {
            return "AsyncObject";
        }
    
        @Override
        public void done() {
            System.out.println("Action done");
        }
    })
    .execute();
```

This object will run the `action()` method in a new thread. Once the `Action` is finished, it will give feedback through the `done()` method.

If we want to get track of the response we will use the `Success` task like this:

```java
new AsyncObject<String>()
    .action(new AsyncObject.Action<String>() {
    
        @Override
        public String action() throws Exception {
            return "AsyncObject";
        }
    
        @Override
        public void done() {
            System.out.println("Action done");
        }
    })
    .success(new AsyncObject.Success<String>() {
    
        @Override
        public void success(String response) {
            System.out.println(response);
        }
    })
    .execute();
```

And if we want to get track of the possible errors with the `Action` we will use the `Error` task like this:

```java
new AsyncObject<String>()
    .action(new AsyncObject.Action<String>() {
    
        @Override
        public String action() throws Exception {
            return "AsyncObject";
        }
    
        @Override
        public void done() {
            System.out.println("Action done");
        }
    })
    .error(new AsyncObject.Error() {
    
        @Override
        public void error(Exception e) {
            System.out.println(e.toString());
        }
    })
    .execute();
```

## The example

In this example, we will use 4 different `AsyncObjects`, each one with a different delay for the response simulating background tasks. All the calls are done inside the method `start()`.

The details for each requests are:

1. It just uses the `Action` task, has 2 seconds delay and returns an error. 
2. Uses the full object (`Action`, `Success`, `Error`) and has 4 seconds delay.
3. Uses the full object and has 3 seconds delay and it's executed in a `ThreadPool`.
4. It just uses the `Action` task, has 5 seconds delay and it's executed in the same `ThreadPool` as the third request.

The result will be something like this (the `-> END METHOD` line can be shown in another position because of the async nature of the threads):

```
-> START METHOD
request 1 started in [Thread-0] (2 seconds delay response)
request 2 started in [Thread-1] (4 seconds delay response)
request 3 started in [pool-1-thread-1] (3 seconds delay response)
request 4 started in [pool-1-thread-2] (5 seconds delay response)
-> END METHOD
java.lang.Exception: Fake error
request 1 done
request 3 response: {user_id="15", user_name="Thelma"}  [pool-1-thread-1]
request 3 done
request 2 response: {user_id="31", user_name="Marcus"}  [Thread-1]
request 2 done
Action successfully completed
request 4 done
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
