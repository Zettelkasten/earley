# Latin->German Earley Translator

This program can read a Latin sentence to translate it into another language (currently only German).
It follows a rule-based approach that extend the concept of Context Free Grammars (CFG) with variable form parameters that denote whether a word is singular/plural, which person, tense, voice, etc. it has.
By extending the CFG's production rules to enforce constraints on the form parameters of symbols, congruencies between different sentences can be enforced.
This is especially important for Latin.
To generate a syntax tree of a given Latin source sentence, we use an adaption of the Earley algorithm, that does not require symbols in a production rule to have a fixed order by explicitly keeping track of which words have been already translated.
The Latin syntax tree can then easily be translated to a German syntax tree using simple replacement rules for every Latin production.
This works especially well with Latin->German, as the syntax and semantics of both languages are very similar. 

The software is structered modularly in several components, which can be swapped out.
Multiple implementations exist, they are no longer maintained and many probably broken (especially those using external resources from the internet).
It also provides detailed output views in HTML format for debugging and further understanding, and can print syntax trees using SVG.
Currently, no proper user interface exist, you have to edit the source code to use the program.

## Quick Start

Clone the repository, and edit the file `src/com/zettelnet/earley/test/LatinRegistryTest.java`, which implements simple translation between Latin to German.
Run and compile the program using Java 1.8+.
You can change the Latin `input`-string to try different locations.
See "Current Latin vocabulary and grammar" for which words / grammatic rules are currently implemented.

Running the program writes several debug outputs:
 - `tokens.html`, the forms of all possible Latin words.
 - `parse.html`, the state charts of the Earley algorithm used for generating the Latin parse tree.
 - `translate.html`, an overview of all translation rules and their application to generate the German parse tree.

The German sentence with the highest probability of being the correct translation is printed to stdout, as well as a Latin and German syntax trees.

## Current Latin vocabulary and grammar

Currently, the implemented vocabulary is rather small (have a look at `src/com/zettelnet/earley/test/LatinRegistry.java`), but this should provide a proof of concept.
The currently implemented grammar used for parsing is also work in progress, for this see `src/com/zettelnet/latin/grammar/LatinGrammar.java`, and the file `grammar.txt`.
