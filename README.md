# Circle Neural Network 4 Java/Json
## What is **cnn4j**
It is a framework for very fast deployment of neural networks using deeplearning4j. You can easily build a neural network for regression models within 10 lines of code. It provides a simple command line interface which lets you control the network. The framework is built for json data: You can provide a single file or a whole directory of json chunks to train/evaluate the network with. It provides a simple flag to start dl4j's web-ui. 
**This framework is not done by any professional machine learning engineer but two students. We would be happy if you will help us on any mistake we made**

## Copyright

```java
/*
 * Copyright 2016 Sebastian Sellmair, Thomas Gilli
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

```

## Setting up
### Add the circle technologies repository to your project
```xml
    <repositories>
        <repository>
            <id>circle-os</id>
            <url>https://circle-technologies.com:9090/repository/circle-os/</url>
        </repository>
    </repositories>
``` 

### Add cnn4j as dependency 
```xml
        <dependency>
            <groupId>com.circle-technologies</groupId>
            <artifactId>cnn4j</artifactId>
            <version>0.2-alpha</version>
        </dependency>
```

### Choose your processing power
deeplearning4j provides the possibility of training networks with your nvidia gpu using cuda. You can choose between native cpu or gpu processing by choosing **one** of the following dependencies. 

#### Native processing (should always work)

```xml
     <dependency>
            <groupId>org.nd4j</groupId>
            <artifactId>nd4j-native-platform</artifactId>
            <version>0.5.0</version>
        </dependency>
```

#### OR GPU based processing (CUDA)

```xml
        <dependency>
            <groupId>org.nd4j</groupId>
            <artifactId>nd4j-cuda-7.5-platform</artifactId>
            <version>0.5.0</version>
        </dependency>
```
You can contact us via sellmair (at) outlook . de for any help. 

## Usage
Currently the Framework has a very simple concept: You have json data containing input variables and output variables in an array. A simple example of data describing a sum would be:

```json
[
  {
    "first_input": 300,
    "second_input": 105,
    "output": 405
  },
  {
    "first_input": 200,
    "second_input": 44,
    "output": 244
  }
]
```


You have to provide a `ParamProvider `to tell the framework in which data you are interested in.

In this example a param provider would look like this:

```java
package com.circle_technologies.cnn4j.example;

import com.circle_technologies.cnn4j.predictive.network.Params;
import com.circle_technologies.cnn4j.predictive.network.SimpleParams;
import com.circle_technologies.cnn4j.predictive.provider.ParamProvider;

/**
 * Created by Sellm on 09.09.2016.
 * Circle Technologies
 */
@SuppressWarnings("WeakerAccess")
public class RegressionParamProvider implements ParamProvider {
    @Override
    public Params provideParams() {
        return new SimpleParams(new String[]{"first_input", "second_input"}, new String[]{"output"});
    }
}

```


As the next step we have to specify the network configuration we want to deploy. We can use the simple pre-built ones for simple tasks.

We can build the network with the specified `ParamProivder` and `NetworkProvider` and start the commandline interface using the 'Predictor': 

```java 
public class RegressionExample {
    public static void main(String[] args) {
        NetworkContext context = new Predictor()
                .setParamProvider(new RegressionParamProvider())
                .setNetworkProvider(new SimpleSingleLayerNetworkProvider())
                .build();

        context.getCommander().setVerbose(false);
    }
}
```

We did not use a custom 'NetworkProvider' in the example above. We used the pre-implemented 'SimpleSingleLayerNetworkProvider' which does not provide any hidden layer. 

You can adjust learning rate and activation function while using those pre-defined 'NetworkProvider':

```java
.setNetworkProvider(new SimpleSingleLayerNetworkProvider()
                .setLearningRate(0.1f)
                .setActivationFunction("tanh"))
```

**And this is all. You created a nerual network within 10 lines of code. Happy training!**

## The Commander
The Commander is the commandline interface to controll the network. You can use the following commands
```
usage: save
 -f,--file <arg>   The folder path to store the network in
 -u,--update       Flag indicating that a previously stored network is
                   allowed to be overwritten

usage: restore
 -f,--file <arg>


usage: train
 -d,--dir <arg>    Path to directory to read in: Reads all files in the
 -e,--epochs <e>   Number of epochs
 -f,--file <arg>   Filepath to a single file to read
 -w,--web-ui
	
usage: eval
specify dir (-d) or file (-f)

```

You will be able to simply add commands in further beta version.

## Bugs
* Exit command does not work correctly (we will fix this later)
