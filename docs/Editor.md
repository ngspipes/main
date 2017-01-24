## NGSPipes Editor

The NGSPipes Editor is a user-friendly editor for graphically define pipelines. Using this editor is very simple to define each processing step of the pipeline (i.e. a command) as well as the data to be used at each step (i.e., arguments). 

The following sections show how to use the editor. 

###Index

1. [Download NGSPipes Editor](#download)

2. [Execute NGSPipes Editor](#execute)

3. [NGSPipes Editor Sections](#head3)

4. [Select the tools repository](#head4)

5. [Create a new pipeline](#head5)

6. [Generate the final pipeline version to execute](#head6)

7. [Load an existing pipeline](#head7)

8. [Multiple loaded pipelines](#head8)

9. [Error Reporting](#head9)

10. [Multiple inputs] (#head10)

###<a name="download"></a> 1. Download NGSPipes Editor


The NGSPipes Editor is a Java Application. To deploy this it in your system you need:

*  Java Runtime Environment (JRE), version 8, which can be obtained from [here](http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html).

Download the editor from [here](https://github.com/ngspipes/editor/releases).

###<a name="execute"></a> 2. Execute NGSPipes Editor

To run the editor, and uncompress the downloaded file. Then
you should have the following file tree:
```
 |-- editor-1.0\
       |-- bin\
          |-- editor        (CUI OSX/Linux run script)
          |-- editor.bat    (CUI Window run script)
       |-- lib\
          |-- ...
```
Then you can simple double click on corresponding script.

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

-<a name="fig3">
-![](https://cloud.githubusercontent.com/assets/1495120/12481272/d732bdd8-c03f-11e5-89c5-6974ff9ba482.png)
-</a>

**Notice that depending on the MAC OS version, it may be necessary to unlock to make changes and to select the option "Allow apps downloaded from Anywhere"**

 
**Figure 3**
 
 The initial GUI that appears from editor is the following:
 



<a name="fig4">
![](https://cloud.githubusercontent.com/assets/1495120/12481598/2047c2fa-c042-11e5-866b-02cf46b2dc9f.png)
</a>

**Figure 4**

In the following sections it will be explained how to use the editor. 
Moreover, in the editor's menu, selecting `help` and then the menu item `about`, it is possible to find some tutorial videos to help to use NGSPipes Editor.

###<a name="head3"></a> 3. NGSPipes Editor Sections

When defining a new pipeline (we will explain how to define a new pipeline in the Section [5](#head5)),  the editor environment will appear similar to one of Figures 5 and 6 (it depends on the edition that is being performed). 

<a name="fig5">
![](https://cloud.githubusercontent.com/assets/1495120/12481511/a8bc09ee-c041-11e5-8559-7d1fe010671c.png)
</a>
**Figure 5**

<a name="fig6">
![](https://cloud.githubusercontent.com/assets/1495120/12481812/9abc28e0-c043-11e5-9da2-2f9c72243212.png)
</a>

**Figure 6**


The NGSPipes Editor is composed of 5 sections: utilities; repository; tools; commands; pipeline and menu bar.
These sections are pointed out in Figure 7.
<a name="fig7">
![](https://cloud.githubusercontent.com/assets/1495120/12640750/84a4a7c0-c5a3-11e5-8986-202716d73197.png)
</a>

**Figure 7**

The **utilities section** includes all the buttons for executing the utilities actions, such as saving the active pipeline, closing it and generate the final version, when in this last case the user is asked to define the input and output directory ((see Section [6](#head6) for more information on pipeline generation). There are also in this section similar buttons for applying these actions for more than one workflow at the same time. Moreover, it is also in this section that exists a button for creating a new pipeline (see Section [5](#head5) for more information).

More specifically,

*  `New` button - Create a new pipeline;
*  `Open` button - Open an existent pipeline;
* `Save` button - Save the active pipeline;
* `Save All`button - Save all the open pipelines;
* `Close` button - Close the active pipeline;
* `Close All` button - Close all the open pipelines;
* `Generate` button - Generate the pipeline in the NGSPipes language, _i.e._, generate the file with extension `.pipes` for the active pipeline. This file is essential for executing the pipeline with the NGSPipes Engine (see https://github.com/ngspipes/engine/wiki for more information). With this action, the user is asked for defining the input and output directory. It is asked if it is allowed to copy the input files that are not already in the Input directory. 
* `Generate All` button - Generate all the files with extension `.pipes` for all the active pipelines. For each pipeline it is applied the action of the `Generate` button. 


###<a name="head4"></a> 4. Select the tools repository

When the Editor starts, it loads a local repository that is included within the tool. However, user can select other repository, either local or remote. To select other repository, go to the menu `Repository` and select `Change repository`.
In the version 1 of the Editor, there are four types that are supported, as depicted in Figure [8](#fig8).

<a name="fig8">
![](https://cloud.githubusercontent.com/assets/1495120/12616409/eb5c9d3e-c502-11e5-841d-199286f275c3.png)
</a>

**Figure 8**

The `Default` is a local repository that is included within the tool. To specify other local repositories, it is necessary to select the `Local` option and, with the `search button`, select the path to the repository. Instead, to specify a remote one, it is necessary to select one of the following options, `github or uribased`, depending on the type of the remote repository. For instance with the tools' repository example, namely https://github.com/ngspipes/tools, please select the option `github`, with the URL `https://github.com/ngspipes/tools`.

Then, it is possible to observe the **repository section** on Figure [9](#fig9). 

<a name="fig9">
![](https://cloud.githubusercontent.com/assets/1495120/12617103/1ba97fea-c506-11e5-8453-2ed3260d8499.png)
</a>

**Figure 9**

In the **repository section**, the user may explore all the tools that are available on the repository, as well as filter them by name. It is also possible to obtain the description of a tool if we place the mouse over the tool's logotype. Selecting a tool, will open the **tool section** (in Figure [5](#fig5), Velvet is selected and the tool section is at the bottom, centered), where is possible to navigate over all the commands available within that tool. In some cases, the tool has only one command, as for instance the Trimmomatic tool. It is also possible to obtain the description of each command similarly as done for obtaining the tool description. In these sections, the user may obtain more information about a given tool, command, input or output, only by selecting a given item of one of these sections with the right button of the mouse and selecting the Description option. This option opens a new window with all the information available on the repository, as depicted in Figure [10](#fig10).

<a name="fig10">
![](https://cloud.githubusercontent.com/assets/1495120/12568263/7bc82796-c3bc-11e5-8ecd-f22c4bbde2e4.png)
</a>

**Figure 10**

The other sections, namely the **pipeline** and **command** sections will be detailed in section [5](#head5).

###<a name="head5"></a> 5. Creating a new Pipeline

In order to create a new pipeline, after selecting the ` tools' repository`, please select the button with a plus (in the utilities section) or go to the menu `File` and select the option `new`.

<a name="fig11">
![](https://cloud.githubusercontent.com/assets/1495120/12617431/92f24db0-c507-11e5-8d85-602125109c12.png)
</a>

**Figure 11**



After defining the directory where the pipeline is kept and the name of the pipeline, it will appear the **pipeline section**, as depicted in the following Figure.

<a name="fig12">
![](https://cloud.githubusercontent.com/assets/1495120/12617738/e28e3414-c508-11e5-8926-1720d22bde41.png)
</a>

The pipeline creation generates a file with extension `.wf`. This file keeps all information of a pipeline within the editor, not only the commands as well as the visual positions of the pipeline within the editor.

**Figure 12**

The **pipeline section** has two panels, the _chain_ and the _order_ panel. 
In the _chain_ panel, the user can add or remove commands as well as set arguments and chains. For instance, in (Figure [5](#fig5)) it was defined a chain between the `trimmomatic command` and the `velveth command` since the output file of the first one is an input file of the second one.
For more information about chains, please see https://github.com/ngspipes/dsl/wiki.
Before defining the chain, it is necessary to add the commands to execute within the pipeline. Adding a new command to the `chain panel` (notice that is necessary to previously select the tool in the `repository section` and then in the `tool section` select the command) is simply done by a _drag and drop_ action.


 After adding a command and double clicking on it, it appears the **command section** (see the right size of Figure [5](#fig5)). This section only appears when the user does a double click on a command over the _chain_ panel. In the **command section**,  the user can set the arguments of the selected commands as well as to confirm its generated output file names. 

For defining the chain between two commands, it is necessary to drag the **blue icon** that appears in the command image within the `chain` panel, after a double click.

<a name="fig13">
![](https://cloud.githubusercontent.com/assets/1495120/12618522/12355762-c50c-11e5-8207-8b86b7051d2c.png)
</a>

**Figure 13**

Then it is necessary to select the **blue icon** and drag it to the command to which is to do the chain operation. After that, it will appear the following figure:

<a name="fig14">
![](https://cloud.githubusercontent.com/assets/1495120/12618572/5cd02acc-c50c-11e5-8672-1ff70ec853bd.png)
</a>
**Figure 14**

Here it is necessary to select the output to be chained as an argument to the other tool. After that, it is necessary to click on the *blue icon* and the chain action will be set and a black arrow will appear between both tools (see next Figure).

<a name="fig15">
![](https://cloud.githubusercontent.com/assets/1495120/12618629/9ba3317c-c50c-11e5-9d3f-21b435aadb5d.png)
</a>
**Figure 15**


After setting the required arguments of all commands added to the pipeline and setting all the chains,
the user can observe in the _order_ panel one of the possible inferred orders of the pipeline execution (see Figure [6](#fig6)). This order is given, assuring that command dependency and priority are preserved. For more information about command dependency and priority, please see https://github.com/ngspipes/tools/wiki. 

Moreover, in the editor's menu, selecting `help` and then the menu item `about`, it is possible to find some tutorial videos to help to use NGSPipes Editor.

###<a name="head6"></a> 6. Generate the final pipeline version to execute

As mentioned before, creating a new pipeline generates a file with extension `.wf` and with the name chosen by the user. This file keeps all information of a pipeline within the editor, not only the commands as well as its visual positions.

However, if the user wants to execute the pipeline in the NGSPipes Engine (https://github.com/ngspipes/engine/wiki), it is necessary to produce a file with extension `.pipes`. This file is written using the NGSPipes language (https://github.com/ngspipes/dsl/wiki) and thus does not have visual information. For producing the file with extension `.pipes` it is necessary to select the generate button or go to `File -> Generate pipeline`.

###<a name="head7"></a> 7. Loading an existing pipeline

To load an existing pipeline, it is necessary to go to `File -> Open` and choose a pipeline file (with extension `.wf`) or select the open button.

###<a name="head8"></a> 8. Multiple loaded pipelines

It is possible to have multiple loaded pipelines, but just one is active.


![](https://cloud.githubusercontent.com/assets/1495120/12640476/0421eba4-c5a2-11e5-883b-8b0fe0edc1e5.png)


###<a name="head9"></a> 9. Error Reporting

Each argument of a command of a given tool has a type and may be or not optional. The required arguments (not optional)
appears in a red box.
The type of each argument must be one of the following: integer number; file; text; real number or a directory. When the user assigns an incompatible type to a given argument, the editor will generate an error message to report that situation. An example of this situation is depicted in the following figure:

![](https://cloud.githubusercontent.com/assets/1495120/19511056/94b39440-95dd-11e6-8b5f-c5cf439ff423.png)

If the user does not set a compatible value to a required argument, the editor will also
generate an error message to report that situation, when generating the pipeline specification (pressing the generate button). 

![](https://cloud.githubusercontent.com/assets/1495120/19511108/c28c0c1c-95dd-11e6-8b65-47b31f0e193e.png)


###<a name="head10"></a> 10. Multiple inputs

The tools can produce multiple outputs. These tools also might require multiple inputs coming from different tools or the same tool. When the multiple inputs are from the same tool, NGSPipes supports it by adding a numbered label on the arrow.
This functionality is depicted in the following figure:

![] (https://cloud.githubusercontent.com/assets/1495120/19566045/db25483e-96e0-11e6-9798-021c448c1f11.png)

Notice that this label is only visible when one of the tools is selected.
