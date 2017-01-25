# NGSPipes engine

The _NGSPipes engine_ starts with a pipeline description and transform it into a sequence of calls to the designated tools. After this, the _NGSPipes engine_ automatically configures and executes each tool in isolation from the remaining system environment (using a dedicated virtual machine).

The _NGSPipes engine_ is available in two flavors: a command line user interface (CUI) and a graphic user interface (GUI). The first is ideal to use when running on remote servers (either physical or deployed as virtual machines in the cloud), although it can also run locally. The second one can be used in systems where a graphical display is available.

The following sections shows how to run the engine. To build from source code please follow these [instructions](https://github.com/ngspipes/engine/wiki/Instructions-to-build-NGS-Pipes-Engine-from-source-code).



## Requirements to run the NGSPipes Engine

The machine where _NGSPipe engine_ is to be executed needs the following tools:

* Java 8 Development Kit (http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html).
* VirtualBox version >= 5.0 (https://www.virtualbox.org/wiki/Downloads). NOTE: Ensure the command `VBoxManage` can be found by the *command line* of your operating system.

## Install NGSPipes Engine

The _NGSPipes engine_ is made of a regular Java application and a VirtualBox's compliant image (also identified as _executor_). To deploy this in your system:

1. Download [engine-2.0-zip](http://link.inesc-id.pt/pipes/engine-2.0.zip) from our file server and uncompress to a working directory (`WD`)
1. Download the _executor_ image from [here](http://link.inesc-id.pt/pipes/NGSPipesEngineExecutor.zip) and uncompress to your work directory (`WD\engine-1.0\`)
1. Follow the instructions bellow to either run in a system in a [text console](#console) or with a [graphical interface](#ui).

After these steps you should have the following file tree:

```
  WD
    |-- engine-1.0\
       |-- NGSPipesEngineExecutor\ 
          |-- NGSPipesEngineExecutor.vbox
          |-- NGSPipesEngineExecutor.vdi
       |-- bin\
          |-- engine        (CUI OSX/Linux run script)
          |-- engine.bat    (CUI Window run script)
          |-- engine-ui     (GUI OSX/Linux run script)
          |-- engine-ui.bat (GUI Window run script)
       |-- lib\
          |-- ...
    |-- (other files, e.g. the pipeline description)

```

##  Run the NGSPipes Engine

The engine is provider as a console application or a graphical user interface application.

###  Engine command line tool

This is a regular Java application packed as a JAR file. To run, use the following command line at the working directory (`WD`):

#### Windows:

``c:\WD>engine-1.0\bin\engine.bat <mandatory arguments> [<optional arguments>]``

#### OSX/Linux

``user@machine:/home/WD$ engine-1.0/bin/engine <mandatory arguments> [<optional arguments>]``

#### Parameters

* The command line tool has the following mandatory parameters :

`-pipes` <arg>      Relative ou absolute path of the pipeline description (mandatory). This file must be a .pipes extension file, where the pipeline is written using the NGSPipes language.

`-in` <arg>         Absolute path at the user machine where the input data files are located (mandatory).

`-out` <arg>        Absolute path at the user machine where the output data file will be placed (mandatory).

* The command line tool has the following *optional* parameters :

 `-cpus` <arg>       Assigned cores. Default is 2 CPUs.

 `-mem` <arg>        Assigned memory (in Gigabytes). If not present, the number of gigabytes allocated to the engine will be inferred by analyzing the tools in the pipeline.  

 `-from` <arg>       Initial pipeline step. If not present, the first step will be executed.

 `-to` <arg>         Final pipeline step. If not present, the pipeline will execute all the steps.

 `-executor` <arg>   Executor image name. If not present, uses the image located at WD/NGSPipesEngineExecutor.

#### Example

A small example with only manadatory arguments (a more complete description is presented in section [Use Case](#usecase)):

```
  engine-1.0/bin/engine -in /home/ngs/inputs -out /home/ngs/outputs -pipes pipeline.pipes
```

#### Error reporting 

The next figure shows an error report from the pipeline engine when executing a pipeline where a mandatory argument is not specified.

[[EngineMustUseArgument-CMD.png]]

The next figure shows an error report from the pipeline engine when executing a pipeline where an argument is uses with a non-compatible value type, according to the tool's specification.

[[EngineWrongArgument-CMD.png]]


### Engine GUI

The GUI version of the _NGSPipes Engine_ allows the same operations but using a graphical interface. When installed at a working directory (`WD`), the tool can be executed in the file explorer of your operating system:

#### Windows

``WD\engine-1.0\bin\engine-ui.bat``

#### OSX/Linux

``WD/engine-1.0/bin/engine-ui``


If you have OSX and you you prefer the double click version to run the editor, it may appears, only the first time after you double click it, the following info:
<a name="fig1">
![Figure 1](https://cloud.githubusercontent.com/assets/1495120/12481114/d82ec584-c03e-11e5-89b7-991b2b94a1b5.png)
</a>

**Figure 1**

Then, go to  "System Preferences" and choose "Security and Privacy"

<a name="fig2">
![](https://cloud.githubusercontent.com/assets/1495120/12481218/80709380-c03f-11e5-9d50-c21bb22fea56.png)
</a>

**Figure 2**

Then select the button "Open anyway"

<a name="fig3">
![](https://cloud.githubusercontent.com/assets/1495120/12481272/d732bdd8-c03f-11e5-89c5-6974ff9ba482.png)
</a>
 
**Figure 3**
 
**Notice that depending on the MAC OS version, it may be necessary to unlock to make changes and to select the option "Allow apps downloaded from Anywhere**
 

#### Screen shots

The following image shows a screenshot of the main windows and a short description of each button. 

[[screen-main.png]]

There are two main tabs: **Recent pipelines** and **Engines**. The **Recent pipelines** tab lists the last pipelines loaded by the application. It also allows the [configuration](#config) of parameters for a selected pipeline. The **Engines** tab shows the previously used instances. In each engine, different tools can already be installed. The user can choose which instance to execute based on his knowledge of the pipeline.

#### Load pipeline

When loading a pipeline the user chooses the file with the pipeline description, the directory at the user's computer where the results are to be written and the path from where the data is to be loaded. The user can also choose which engine instance to use.

[[screen-load.png]]

#### Configure pipeline

When a pipeline description in already loaded by the UI, several execution parameters can be changed: paths, engine instance, memory and number of cores.

[[screen-config.png]]

#### Execute pipeline

When an engine instance is selected and the "Run Pipeline" button is pressed, the UI will show the following window, where output information regarding the execution steps are presented.

[[screen-execute.png]]

#### Error reporting

Errors can occur during the execution of a pipeline. For example, the next figure shows an error related to a mandatory argument that is not specified.

[[EngineMustUseArgument.png]]

The next figure shows an error related to a mismatch between the type of value used in the pipline and the type of parameter that is present in the specification of the tool.

[[EngineWrongArgument.1.png]]



###  Use case

The following use case executes the pipeline described in the [DSL section](https://github.com/ngspipes/dsl/wiki#head3) using the console version of *NGSEngine*. The tools' repository used in the pipeline is [https://github.com/ngspipes/repository](https://github.com/ngspipes/repository). It has metadata for the tools *Trimmomatic*, *Velvet* and *Blast*.

* Check if the [requirements](#requirements) are met and that the engine and executor image are [installed](#install).
* Download the pipeline [here](https://link.inesc-id.pt/pipes/example/pipeline.pipes) and save it as `pipeline.pipes` to the working directory. The following examples assume the working directory is `c:\ngspipes`.
* Download the input data [sample](https://link.inesc-id.pt/pipes/example/) and place it at `inputs` directory (other name can be chosen). This data set comes from the [NCBI SRA](http://trace.ncbi.nlm.nih.gov/Traces/sra/?run=ERR406040), being part of a project on *deep sequencing within the Streptococcus pneumoniae antibiotic resistant pandemic clone PMEN1*. Extra information on how this data was obtained can be obtained [here](https://link.inesc-id.pt/pipes/data/).

After unziping file `ERR406040.fastq.zip` the directory content will look like:

```
c:\ngspipes\inputs
   |-- allrefs.fna.pro 
   |-- ERR406040.fastq
   |-- NexteraPE-PE.fa
   |-- TruSeq2-PE.fa
   |-- TruSeq2-SE.fa   
   |-- TruSeq3-SE.fa
   |-- TruSeq3-PE-2.fa
   |-- TruSeq3-PE.fa
   |-- TruSeq3-SE.fa 
```

* Create the `outputs` directory (`c:\ngspipes\outputs`)
* Execute the *engine* at your working directory using the following command line:

#### Windows
``c:\ngspipes>engine-1.0\bin\engine.bat -in c:\ngspipes\inputs -out c:\ngspipes\outputs -pipes c:\ngspipes\pipeline.pipes``

#### OSX/Linux
``ngs@server:/home/ngspipes$engine-1.0/bin/engine -in /home/ngspipes/inputs -out /home/ngspipes/outputs -pipes /home/ngspipes/pipeline.pipes``

#### Example and description of output messages

Initial steps of the output will look like this:

```
Loading engine directories
Loading engine resources
Using classpath C:/Users/user/NGSPipes/Engine/dsl-1.0.jar;C:/Users/user/NGSPipes/Engine/repository-1.0.jar
Getting engine requirements
Getting clone engine
Clonning engine
...... Clonning engine
...... Clonning engine
...... Clonning engine
...... Clonning engine
...... Clonning engine
...... Clonning engine
Configurating engine
Starting execute engine
Booting engine and installing necessary packages
...
```

Note that the cloning step only happens in the first execution of the engine. On the other hand, when a tool is used for the first in any *pipeline*, the engine will automatically download and install the corresponding Docker image. An example of output for when this is necessary is presented for the *Trimmomatic* tool:

```
...
TRACE    :: STARTED ::
TRACE   Running -> Step : 1 Tool : Trimmomatic Command : trimmomatic
INFO    Executing : sudo docker run -v /home/ngspipes/Inputs/:/shareInputs/:rw -v /home/ngspipes/Outputs/:/shareOutputs/:rw ngspipes/trimmomatic0.33 java -jar trimmomatic-0.33.jar SE -phred33 /shareInputs/ERR406040.fastq /shareOutputs/ERR406040.filtered.fastq  ILLUMINACLIP:/shareInputs/adapters/TruSeq3-SE.fa:2:30:10 SLIDINGWINDOW:4:15 LEADING:3 TRAILING:3   MINLEN:36
INFO    Unable to find image 'ngspipes/trimmomatic0.33:latest' locally
INFO    latest: Pulling from ngspipes/trimmomatic0.33
INFO    511136ea3c5a: Pulling fs layer
INFO    e977d53b9210: Pulling fs layer
INFO    c9fa20ecce88: Pulling fs layer
...
INFO    6cf3f4911f80: Download complete
INFO    Digest: sha256:44f1dea760903cdce1d75c4c9b2bd37803be2e0fbbb9e960cd8ff27048cbb997
INFO    Status: Downloaded newer image for ngspipes/trimmomatic0.33:latest
INFO    TrimmomaticSE: Started with arguments: -phred33 /shareInputs/ERR406040.fastq /shareOutputs/ERR406040.filtered.fastq ILLUMINACLIP:/shareInputs/adapters/TruSeq3-SE.fa:2:30:10 SLIDINGWINDOW:4:15 LEADING:3 TRAILING:3 MINLEN:36
...
```

Note that this tool was previously *dockerized* by the NGSPipes team. For other tools, such as Velvet or Blast, there is already public Docker images which the example pipeline uses. 

When the execution finish, the following files will be at the working directory:
```
c:\ngspipes\outputs
   |-- allrefs.phr
   |-- allrefs.pin
   |-- allrefs.psq
   |-- blast.out
   |-- filtered.fastq
   |-- velvetdir/
      |-- Log
      |-- Roadmaps
      |-- Sequences
      |-- contigs.fa
      |-- LastGrpah
      |-- stats.txt
```

#### Execution times

<a href="#exectimes"></a>

The above example was executed using several hardware configurations and operating systems. The pipeline was executed with the command line:

```
ngs@server:/home/ngspipes$engine-1.0/bin/engine -in /home/ngspipes/inputs -out /home/ngspipes/outputs -pipes /home/ngspipes/pipeline.pipes
```

The folowing table shows execution times measured on *cold* and *warm* start situations. Cold start happens when the engine is executed the first time after installation. Warm start represents the situation when a pipeline is being re-executed and no updates are necessary to the tools.

| OS          | CPU                 | RAM (GB) | Disk | Cold start | Warm start |
|-------------|---------------------|----------|------|------------|------------|
| Windows 10  | Intel i5 @ 2.53 Ghz |    8     | SSD  |   30 min.  |   22 min.  |
| Windows 10  | Intel i7 @ 3.5 Ghz  |    16    | HDD  |   23 min.  |   16 min.  |
| OSX         |                     |          | SSD  |            |            |

<sub>(*) [http://www.speedtest.net/](http://www.speedtest.net/)</sub>

As expected, a cold start takes an extra time because of intial setup and download of the tools used in the [pipeline](https://link.inesc-id.pt/pipes/example/pipeline.pipes). Warm start is the common execution scenario. These values can vary depending on:

* the size of the input data;
* the number of tools and commands used in pipeline;
* the input data and resources assigned to the execution image (CPU (`-cpus`) and memory (`-mem`)).


##  Appendix: Libraries dependencies

The _NGSPipes engine_ is dependent of the following libraries:

* JSON (JSON-java)[http://www.json.org/java/index.html]
* ANTLR - ANother Tool for Language Recognition (ANTLR-complete)[http://www.antlr.org/download.html]