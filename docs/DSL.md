#  NGSPipes DSL

The NGSPipes DSL is a domain specific language for describing pipelines. The syntax is described following a EBNF notation alike. As a programming language, it has some primitive building blocks with the expressiveness to define data processing, namely flow processing can be modeled as a direct acyclic graph. These primitives are defined by syntactic and semantic rules which describe their structure and meaning respectively. 
The primitives and the full syntax will be presented in this section. For further explaining the expressiveness of each primitive, we also incrementally introduce an example in this section, as well as the full example.

## Primitives

The primitives of NGSPipes DSL are  `Pipeline`, `tool`, `command`, `argument` and `chain`. In the folowing subsections it will be introduced the purpose of this primitives, ilustrating with some examples.

###  Pipeline

Since a `Pipeline` is composed by the execution of one or more tools, it must be defined the tools repository, i.e., all the information necessary with respect to the available tools. To define this repository in the pipeline it is necessary to identify not only where it is stored, but also the type of storage (localy ou remotely, like github) to know how to process that information. In Example 2.1 is depicted a part of a pipeline specification.


```javascript  
Pipeline "Github" "https://github.com/ngspipes/tools"{ 
```
**Example 2.1: A partial pipeline specification, using a remote repository.**

In the example of listing 2.1, "Github" is the repository type and "https://github.com/ngspipes/tools" is the location of the tool repository.
The case of being a local repository is very similar, as it can be observed in Example 2.2.

```
Pipeline "Local" "E:\ngspipes" {
```
**Example 2.2: A partial pipeline specification, using a local repository.**

In the previous example, the tool repository is on the directory named as "ngspipes", found at drive "E:".
Formally,  the pipeline must follow the grammar in Listing 2.1.

``pipeline: ’Pipeline’ repositoryType repositoryLocation ’{’ (tool)+ ’}’ ;``

**Listing 2.1: Partial specification of the DSL grammar: pipeline specification grammar** 


In Listing 2.1, `(tool)+` represents that a pipeline is composed by the execution of one or more tools (notice that, as will be further explained, the tool execution may include the execution of one or more commands).

### tool

Each tool is specified in the pipeline by its name, its configuration file name (without extension) and by the set of commands within the tool that will be executed within this pipeline. For instance, in Example 2.3 the pipeline is composed only by one tool, which only includes a command.

```
javascript
Pipeline "Github" "https://github.com/ngspipes/tools" {
  tool "Trimmomatic" "DockerConfig" {
    command "trimmomatic" {
      argument "mode" "SE"
      argument "quality" "-phred33"
      argument "inputFile" "ERR406040.fastq"
      argument "outputFile" "ERR406040.filtered.fastq"
      argument "fastaWithAdaptersEtc" "adapters/TruSeq3-SE.fa"
      argument "seed mismatches" "2"
      argument "palindrome clip threshold" "30"
      argument "simple clip threshold" "10"
      argument "windowSize" "4"
      argument "requiredQuality" "15"
      argument "leading quality" "3"
      argument "trailing quality" "3"
      argument "minlen length" "36"
    }
   }
  }
```
**Example 2.3: A pipeline specification composed only by one tool, including only one command.** 

The tool configuration file name  in Listing 2.4 is "DockerConfig", _i.e._, it must exist in the tool repository "https://github.com/ngspipes/tools", within the tool information "https://github.com/ngspipes/tools/tree/master/Trimmomatic" (notice that this repository structure is directory based, as explained in https://github.com/ngspipes/tools/wiki), a configuration file named "DockerConfig", with JSON Format. This file must define a JSON object with the property `builder` set as "DockerConfig". In this case, this JSON file is https://github.com/ngspipes/tools/blob/master/Trimmomatic/DockerConfig.json.
With this information together with the repository information, the environment for executing the Trimmomatic command is specified.

### command

As mentioned before, there may exist a set of commands within the tool that should be executed within a pipeline. Example 2.4 depicts an example with this feature.

```javascript
Pipeline "Github" "https://github.com/ngspipes/Repository" {
  tool "Trimmomatic" "DockerConfig" {
    command "trimmomatic" {
      argument "mode" "SE"
      argument "quality" "-phred33"
      argument "inputFile" "ERR406040.fastq"
      argument "outputFile" "ERR406040.filtered.fastq"
      argument "fastaWithAdaptersEtc" "adapters/TruSeq3-SE.fa"
      argument "seed mismatches" "2"
      argument "palindrome clip threshold" "30"
      argument "simple clip threshold" "10"
      argument "windowSize" "4"
      argument "requiredQuality" "15"
      argument "leading quality" "3"
      argument "trailing quality" "3"
      argument "minlen length" "36"
    }
  }
  tool "Velvet" "DockerConfig" {
    command "velveth" {
      argument "output_directory" "velvetdir"
      argument "hash_length" "21"
      argument "file_format" "-fastq"
      chain "filename" "outputFile"
    }
    command "velvetg" {
      argument "output_directory" "velvetdir"
      argument "-cov_cutoff" "5"
    }
  }
```
**Example 2.4: A pipeline specification composed by more than one tool and more than one command.** 

In example depicted in Example 2.4, the pipeline will run two tools, where the second one executes two commands of the Velvet tool, namely `velveth`and `velvetg`.

Therefore, the tools specification must follow the grammar presented in Listing 2.2.

``tool: 'tool' toolName configurationName '{' (command)+ '}' ``

**Listing 2.2:  Partial specification of the DSL grammar: tool specification grammar**

In Listing 2.2 `(command)+` represents that there may exist set of commands with at least a command, within the tool that should be executed within a pipeline.

For executing each command, it is necessary to identify its name, which is unique in the tool context and to set the values for each required parameters (optional parameters may not be specified). We refer the command parameters in NGSPipes language as  _arguments_, since we only specify in the pipeline the parameters which we have values to set. For instance,
in the previous pipeline example, the argument `filename` of the command `velveth` has as value `-fastq`, _i.e._, the input file for this command has a FASTQ format.

Thus, the command specification must follow the grammar in Listing 2.3.

``command : 'command' commandName '{' (argument | chain)+ '}' ``;

**Listing 2.3:  Partial specification of the DSL grammar: command specification grammar**


In Listing 2.3  `(argument | chain)+`
represents that there may exist a list of arguments within this command as well as a list of chains. Chain is also a primitive in NGSPipes, as we will further explain in the subsection _chain_.

### argument

As defined in the previous example, the argument definition has the syntax
presented in Listing 2.4.

``argument : 'argument' argumentName argumentValue ``;

**Listing 2.4: argument syntax.**

For instance, in the previous pipeline specification the `format_file` is an argument for the `velveth` tool, namely:
```javascript
argument "file_format" "-fastq"
```

### chain

 The `chain` primitive  allows to set an argument of a command with the produced output of other command.
Sometimes the produced output is returned as files with names given internally by the command. Alternatively the output files name may be given explicitly as an argument to the command.
In both situations, it is common that other commands use these output files for continue processing the pipeline. For instance,
consider the following example:

```javascript
Pipeline "Github" "https://github.com/ngspipes/Repository" {
  tool "Trimmomatic" "DockerConfig" {
    command "trimmomatic" {
      argument "mode" "SE"
      argument "quality" "-phred33"
      argument "inputFile" "ERR406040.fastq"
      argument "outputFile" "ERR406040.filtered.fastq"
      argument "fastaWithAdaptersEtc" "adapters/TruSeq3-SE.fa"
      argument "seed mismatches" "2"
      argument "palindrome clip threshold" "30"
      argument "simple clip threshold" "10"
      argument "windowSize" "4"
      argument "requiredQuality" "15"
      argument "leading quality" "3"
      argument "trailing quality" "3"
      argument "minlen length" "36"
    }
 }
  tool "Velvet" "DockerConfig" {
    command "velveth" {
      argument "output_directory" "velvetdir"
      argument "hash_length" "21"
      argument "file_format" "-fastq"
      chain "filename" "outputFile"
    }
    command "velvetg" {
      argument "output_directory" "velvetdir"
      argument "-cov_cutoff" "5"
    }
  }
  tool "Blast" "DockerConfig" {
    command "makeblastdb" {
      argument "-dbtype" "prot"
      argument "-out" "allrefs"
      argument "-title" "allrefs"
      argument "-in" "allrefs.fna.pro"
    }
    command "blastx" {
      chain "-db" "-out"
      chain "-query" "Velvet" "velvetg" "contigs_fa"
      argument "-out" "blast.out"
    }
  }
}
```

**Example 2.5: A pipeline specificaton using the chain primitive.**

As it can be seen in Example 2.5, in command `blastx`, the argument `query` receives as value the file ``contigs_fa'', which is an output of the command \verb+velvetg+ of the tool `velvet` (notice that in this case, the name of the file is given internally by the command). 

The primitive `chain` has a simplified version, which can be used when the output is from a the previous command in the pipeline specification. In this case, we only specify the name of the output file to chain with the given argument. As an example, we can see the argument `filename` of the `velveth` command chained with the output file, named as `outputFile`, of the  command `trimmomatic`. 

A last version of the primitive `chain` is when the name of the tool can be omitted, but it is necessary to specify the name of the command, of the argument and also the output. This apply to cases where the chain occurs between two commands of the same tool.

Thus, the chain specification must follow the grammar depicted in Listing 2.5.

``chain : 'chain' argumentName ( ( toolName )? commandName)? outputName ``;

**Listing 2.5:  Partial specification of the DSL grammar: chain specification grammar**

## Full NGSPipes DSL syntax

In Listing 2.6 is depicted the full NGSPipes DSL grammar.

``pipeline: 'Pipeline' repositoryType repositoryLocation '{' (tool)+ '}' `` ;

``tool: 'tool' toolName configurationName '{' (command)+ '}' ``;

``command : 'command' commandName '{' (argument | chain)+ '}' ``;

``argument : 'argument' argumentName argumentValue ``;

``chain : 'chain' argumentName ( ( toolName )? commandName)? outputName ``;

``repositoryType : String ``;

``repositoryLocation : String ``;

``toolName : String ``;

``configurationName : String ``;

``commandName : String ``;

``argumentName : String ``;

``argumentValue : String ``;

``outputName : String ``;

``toolPos: Digit ``;

``commandPos : Digit ``;

``String : ’"’ (ESC | ~["\\])* '"' ``;

``Digit : [0-9]+ ``;

``fragment ESC : '\\' (["\\/bfnrt] | UNICODE) ``;

``fragment UNICODE : 'u' HEX HEX HEX HEX``; 

``fragment HEX : [0-9a-fA-F]``;

``WS : [ \t\r\n]+ -> skip`` ;

**Listing 2.6: Specification of the NGSPipes Full DSL grammar.**

## Examples

###  A pipeline used on epidemiological surveillance 

In this section we present a pipeline used on epidemiological surveillance.
The aim is to characterize bacterial strains through allelic profiles . When sequencing a bacterial strain by paired end methods with desired depth of coverage of 100x (in average each position in the genome will be covered by 100 reads), the output from the sequencer will be two FASTQ files containing the reads. Each read typically will have 90-250 nucleotides length, using Illumina technology. The first data processing step is to trim the reads for removing the adapters used in the sequencing process and any tags used to identify the experiment in a run.

In de novo assembly, software such as Velvet is used to obtain a draft genome composed of contigs, longer DNA sequences resulting from assembling multiple reads. The draft genome can be compared to databases of gene alleles for multiple loci using BLAST. Given BLAST results we can create an allelic profile characterizing the strain.


```
Pipeline "Github" "https://github.com/ngspipes/tools" {
  tool "Trimmomatic" "DockerConfig" {
    command "trimmomatic" {
      argument "mode" "SE"
      argument "quality" "-phred33"
      argument "inputFile" "ERR406040.fastq"
      argument "outputFile" "ERR406040.filtered.fastq"
      argument "fastaWithAdaptersEtc" "adapters/TruSeq3-SE.fa"
      argument "seed mismatches" "2"
      argument "palindrome clip threshold" "30"
      argument "simple clip threshold" "10"
      argument "windowSize" "4"
      argument "requiredQuality" "15"
      argument "leading quality" "3"
      argument "trailing quality" "3"
      argument "minlen length" "36"
    }
 }
  tool "Velvet" "DockerConfig" {
    command "velveth" {
      argument "output_directory" "velvetdir"
      argument "hash_length" "21"
      argument "file_format" "-fastq"
      chain "filename" "outputFile"
    }
    command "velvetg" {
      argument "output_directory" "velvetdir"
      argument "-cov_cutoff" "5"
    }
  }
  tool "Blast" "DockerConfig" {
    command "makeblastdb" {
      argument "-dbtype" "prot"
      argument "-out" "allrefs"
      argument "-title" "allrefs"
      argument "-in" "allrefs.fna.pro"
    }
    command "blastx" {
      chain "-db" "-out"
      chain "-query" "Velvet" "velvetg" "contigs_fa"
      argument "-out" "blast.out"
    }
  }
}
```
**Example 2.6: A pipeline used on epidemiological surveillance.**

A visual representation of this pipeline described in Example 2.6 is presented in the Figure 2.1. Moreover, in this figure is also possible to observe other execution orders that are feasible to execute this pipeline in the engine for workstation.

![image](_Images/dsl_fig1.png)

**Figure 2.1: Visual representation of the execution, in the engine for workstation, of the pipeline described in Example 2.6.**

In the engine for cloud, different steps of the pipeline can be  executed in different machines, it is only necessary to respect its depedencies, as it is shown in the Figure 2.2.


![image](_Images/dsl_fig1_parallel.png)

**Figure 2.2: Visual representation of the execution, in the engine for cloud, of the pipeline described in Example 2.6.**

### A pipeline used on ChiP-Seq analysis 

In this section we present a pipeline used on ChiP-Seq analysis. This 
 pipeline includes mapping with bowtie2, converting the output to bam format, sorting the bam file, creating a bam index file, running flagstat command, and removing duplicates with picard. So, this pipeline can be used in a ChiP-Seq pipeline that uses the resulting bam file for peak calling and creating heatmaps. Since those steps are generic that can be used for ATAC-Seq analysis too.  


```
Pipeline "Github" "https://github.com/ngspipes/tools" {
	tool "Bowtie2" "DockerConfig" {
		command "bowtie2-build" {
			argument "reference_in" "sequence.fasta"
			argument "bt2_base" "sequence"
		}
	}
	tool "Bowtie2" "DockerConfig" {
		command "bowtie2" {
			argument "-U" "SRR386886.fastq"
			argument "-x" "sequence"
			argument "--trim3" "1"
			argument "-S" "eg2.sam"
		}
	}
	tool "SAMTools" "DockerConfig" {
		command "view" {
			argument "-b" "-b"
			argument "-o" "eg2.bam"
			chain "input" "-S"
		}
	}
	tool "SAMTools" "DockerConfig" {
		command "sort" {
			argument "-o" "eg2.sorted.bam"
			chain "input" "-o"
		}
	}
	tool "Picard" "DockerConfig" {
		command "MarkDuplicates" {
			chain "INPUT" "-o"
			argument "OUTPUT" "marked_duplicates.bam"
			argument "REMOVE_DUPLICATES" "true"
			argument "METRICS_FILE" "metrics.txt"
		}
	}
}
```
**Example 2.7: A pipeline used on ChiP-Seq analysis.**

A visual representation of this pipeline is presented in the next figure.

![image](_Images/dsl_fig2.png)

**Figure 2.3: Visual representation of the execution, in both engines, of the pipeline described in Example 2.6.**

### A pipeline using listing tools (for executing only with Engine for Cloud)

A specific use of NGS data in public health is the determination of the relationship between samples potentially associated with a foodborne pathogen outbreak. This relationship can be determined from the phylogenetic analysis of a DNA sequence alignment containing only variable positions, which we refer to as a SNP matrix. The applications of such a matrix include inferring a phylogeny for systematic studies and determining within traceback investigations whether a clinical sample is significantly different from environmental/product samples.

This case study is a pipeline which combines all the steps necessary to construct a reference-based SNP matrix from an NGS sample data set.The pipeline starts with the mapping of NGS reads to a reference genome using Bowtie2, then it continues with the processing of those mapping (BAM) files using SAMtools, identification of variant sites using VarScan3, and ends with the production of a SNP matrix using custom Python scripts (calling of SNPs at each variant site, combining the SNPs into a SNP matrix). The Python scripts are reused from the _CFSAN SNP Pipeline: an automated method for constructing SNP matrices from next-generation sequence data. PeerJ Computer Science 1:e20 https://doi.org/10.7717/peerj-cs.20._
As it can be observed in this data set, there are four samples, whose dataflow process is more detailed in the [documentation page](http://snp-pipeline.readthedocs.io/en/latest/dataflow.html) of this pipeline.


```
Pipeline "Github" "https://github.com/Vacalexis/tools" {
	tool "snp-pipeline" "DockerConfig" {
		command "create_sample_dirs" {
			argument "-d" "snp-pipeline-master/snppipeline/data/lambdaVirusInputs/samples/*"
			argument "--output" "snp-pipeline-master/snppipeline/data/lambdaVirusInputs/sampleDirectories.txt"
		}
	}

	tool "Bowtie2" "DockerConfig" {
		command "bowtie2-build" {
	  		argument "reference_in" "snp-pipeline-master/snppipeline/data/lambdaVirusInputs/reference/lambda_virus.fasta"
	  		argument "bt2_base" "reference"
		}
		command "bowtie2" {
	  		argument "-p" "1"
	  		argument "-q" "-q"
	  		argument "-x" "reference"
	  		argument "-1" "snp-pipeline-master/snppipeline/data/lambdaVirusInputs/samples/sample1/sample1_1.fastq"
	 		argument "-2" "snp-pipeline-master/snppipeline/data/lambdaVirusInputs/samples/sample1/sample1_2.fastq"
	  		argument "-S" "snp-pipeline-master/snppipeline/data/lambdaVirusInputs/reads1.sam"
		}
		command "bowtie2" {
	  		argument "-p" "1"
	  		argument "-q" "-q"
	  		argument "-x" "reference"
	  		argument "-1" "snp-pipeline-master/snppipeline/data/lambdaVirusInputs/samples/sample2/sample2_1.fastq"
	  		argument "-2" "snp-pipeline-master/snppipeline/data/lambdaVirusInputs/samples/sample2/sample2_2.fastq"
	  		argument "-S" "snp-pipeline-master/snppipeline/data/lambdaVirusInputs/reads2.sam"
		}
		command "bowtie2" {
	  		argument "-p" "1"
	  		argument "-q" "-q"
	  		argument "-x" "reference"
	  		argument "-1" "snp-pipeline-master/snppipeline/data/lambdaVirusInputs/samples/sample3/sample3_1.fastq"
	  		argument "-2" "snp-pipeline-master/snppipeline/data/lambdaVirusInputs/samples/sample3/sample3_2.fastq"
	  		argument "-S" "snp-pipeline-master/snppipeline/data/lambdaVirusInputs/reads3.sam"
		}
		command "bowtie2" {
	  		argument "-p" "1"
	 		argument "-q" "-q"
	 		argument "-x" "reference"
			argument "-1" "snp-pipeline-master/snppipeline/data/lambdaVirusInputs/samples/sample4/sample4_1.fastq"
			argument "-2" "snp-pipeline-master/snppipeline/data/lambdaVirusInputs/samples/sample4/sample4_2.fastq"
	  		argument "-S" "snp-pipeline-master/snppipeline/data/lambdaVirusInputs/reads4.sam"
		}
	
	}
	tool "Listing" "DockerConfig" {
		command "startListing" {
			argument "referenceName" "reads.sam"
			argument "filesList" "snp-pipeline-master/snppipeline/data/lambdaVirusInputs/reads1.sam snp-pipeline-master/snppipeline/data/lambdaVirusInputs/reads2.sam snp-pipeline-master/snppipeline/data/lambdaVirusInputs/reads3.sam snp-pipeline-master/snppipeline/data/lambdaVirusInputs/reads4.sam"
		}
	}
	tool "Samtools" "DockerConfig" {
  
    		command "view" {
	  		argument "-b" "-b"
	  		argument "-S" "-S"
	  		argument "-F" "4"
	  		argument "-o" "reads.unsorted.bam"
	  		argument "input" "reads.sam"
		}
    		command "sort" {
	  		argument "-o" "reads.sorted.bam"
	  		argument "input" "reads.unsorted.bam"
		}
		command "mpileup" {
	  		argument "--fasta-ref" "snp-pipeline-master/snppipeline/data/lambdaVirusInputs/reference/lambda_virus.fasta"
	  		argument "input" "reads.sorted.bam"
	  		argument "--output" "reads.pileup"
		}
	}
	tool "VarScan" "DockerConfig" {
  		command "mpileup2snp" {
	  		argument "mpileupFile" "reads.pileup"
	  		argument "--min-var-freq" "0.90"
	  		argument "--output-vcf" "1"
	  		argument "output" "var.flt.vcf"
		}
	}
	tool "Listing" "DockerConfig" {
		command "stopListing" {
			argument "referenceName" "var.flt.vcf"
			argument "destinationFiles" "snp-pipeline-master/snppipeline/data/lambdaVirusInputs/samples/sample1/var.flt.vcf snp-pipeline-master/snppipeline/data/lambdaVirusInputs/samples/sample2/var.flt.vcf snp-pipeline-master/snppipeline/data/lambdaVirusInputs/samples/sample3/var.flt.vcf snp-pipeline-master/snppipeline/data/lambdaVirusInputs/samples/sample4/var.flt.vcf"
		}
	}
	tool "snp-pipeline" "DockerConfig" {
		command "create_snp_list" {
	  		argument "--vcfname" "var.flt.vcf"
	  		argument "--output" "snp-pipeline-master/snppipeline/data/lambdaVirusInputs/snplist.txt"
	  		argument "sampleDirsFile" "snp-pipeline-master/snppipeline/data/lambdaVirusInputs/sampleDirectories.txt"
		}
	}
	tool "Listing" "DockerConfig" {
		command "restartListing" {
			argument "referenceName" "reads.pileup"
		}
	}
	tool "snp-pipeline" "DockerConfig" {
		command "call_consensus" {
	 		argument "--snpListFile" "snp-pipeline-master/snppipeline/data/lambdaVirusInputs/snplist.txt"
	  		argument "--output" "consensus.fasta"
	  		argument "--vcfFileName" "consensus.vcf "
	  		argument "allPileupFile" "reads.pileup"
		}
	}
	tool "Listing" "DockerConfig" {
		command "stopListing" {
			argument "referenceName" "consensus.fasta"
			argument "destinationFiles" "snp-pipeline-master/snppipeline/data/lambdaVirusInputs/samples/sample1/consensus.fasta snp-pipeline-master/snppipeline/data/lambdaVirusInputs/samples/sample2/consensus.fasta snp-pipeline-master/snppipeline/data/lambdaVirusInputs/samples/sample3/consensus.fasta snp-pipeline-master/snppipeline/data/lambdaVirusInputs/samples/sample4/consensus.fasta"
		}
	}
	tool "snp-pipeline" "DockerConfig" {
		command "create_snp_matrix" {
	  		argument "sampleDirsFile" "snp-pipeline-master/snppipeline/data/lambdaVirusInputs/sampleDirectories.txt"
	  		argument "--consFileName" "consensus.fasta"
	  		argument "--output" "snp-pipeline-master/snppipeline/data/lambdaVirusInputs/snpma.fasta"
		}
	}
}
```

![image](_Images/dsl_fig3.jpeg)

**Figure 2.4: Figure from Davis S, Pettengill JB, Luo Y, Payne J, Shpuntoff A, Rand H, Strain E. (2015) CFSAN SNP Pipeline: an automated method for constructing SNP matrices from next-generation sequence data. PeerJ Computer Science 1:e20 https://doi.org/10.7717/peerj-cs.20**

