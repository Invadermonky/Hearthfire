# Description
This is the folder for all the Hearthstone modules. The gradle will need to be remapped to build multiple jars from these module subdirectories.
- core (needs rename) - will hold most of the builder code and the main integrations (GroovyScript, Crafttweaker, JEI)
  - **IMPORTANT** the core module will not add any in-game items/registries. It can have all the block/item code but will not add anything itself. This means that certain additions such as potion effects will be isolated within their respective modules.
- decorations - decorations module
- farmandfeast - will hold all the code associated with foods, feasts, and crops
- hearthandhome - the light-magic system built around base building
- husbandry - animal foods and any AI improvements
- integrations - any integrations that do not fall into the integrations included in the 'core' module
- oceansbounty - Oceanic Expanse compat, work with SirSquidly for this

See (Second Answer) https://stackoverflow.com/questions/20008324/gradle-multiple-jars-from-single-source-folder
