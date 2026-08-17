# Structure Adding Guide
1. You should have a NBT file of the structure before even starting to add it
2. Add biome tags in ModBiomeTagsProvider
   - This includes a tag, defined in ModTags, where the structure shall be generated
3. Add the structure key at ModStructures
4. Add the structure set at ModStructureSets
5. Add the template pool at ModStructureTemplateProvider
6. (Optional) Add the structures to a structure tag