# NGSPipes main repository 

Mostly [gradle](http://gradle.org/) integration and references to other submodules.
You can learn more about git submodules in 
[git documentation](https://git-scm.com/book/en/v2/Git-Tools-Submodules). 

To build _NGSPipes_ components we should start by cloning the [git repo](https://github.com/ngspipes/main) to a local working directory. Then the following commands will build all the components, [DSL](https://github.com/ngspipes/dsl/wiki), [Tools repository](https://github.com/ngspipes/tools/wiki) and the [Engine](https://github.com/ngspipes/engine/wiki) (both console and UI version):
   * `cd main`
   * `git submodule init`
   * `git submodule update`
   * `gradlew build`

The last command will install [gradle](http://gradle.org/whygradle-build-automation/), if necessary.

To generate the `engine-1.0.zip` and `editor-1.0.zip` distribution files, we can run `gradlew distZip`. The files will be located at the respective `build/distributions` directory.
