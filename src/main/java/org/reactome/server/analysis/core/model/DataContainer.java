package org.reactome.server.analysis.core.model;

import org.reactome.server.analysis.core.data.AnalysisDataUtils;
import org.reactome.server.analysis.core.util.MapSet;

import java.io.Serializable;
import java.util.Map;

/**
 * Contains the different data structures for the binary data and also provides
 * methods to initialize the data structure after loading from file and to
 * "prepare" the data structure for serialising
 * <p/>
 * PLEASE NOTE
 * The pathway location map is kept separately because at some point splitting
 * or cloning the pathway hierarchies will be needed, so keeping a map will
 * help to perform this task making it easier and faster.
 * Linking from the physical entity graph nodes to the pathway hierarchy is an
 * option that improves the binary time but makes the splitting or cloning
 * tasks MORE difficult and slow
 *
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public class DataContainer implements Serializable {

    DatabaseInfo databaseInfo;

    //A double link hierarchy tree with the pathways for each species
    Map<SpeciesNode, PathwayHierarchy> pathwayHierarchies;
    //A map between pathways identifier and their locations in the pathway hierarchy
    MapSet<Long, PathwayNode> pathwayLocation;

    //A radix-tree with (identifier -> HierarchyNode)
    IdentifiersMap<EntityNode> entitiesMap;

    IdentifiersMap<InteractorNode> interactorsMap;

    //A double link graph with the representation of the physical entities
    EntitiesContainer entitiesContainer;

    public DataContainer(DatabaseInfo databaseInfo,
                         Map<SpeciesNode, PathwayHierarchy> pathwayHierarchies,
                         MapSet<Long, PathwayNode> pathwayLocation,
                         EntitiesContainer entitiesContainer,
                         IdentifiersMap<EntityNode> entitiesMap,
                         IdentifiersMap<InteractorNode> interactorsMap) {
        this.databaseInfo = databaseInfo;
        this.pathwayHierarchies = pathwayHierarchies;
        this.entitiesContainer = entitiesContainer;
        this.pathwayLocation = pathwayLocation;
        this.entitiesMap = entitiesMap;
        this.interactorsMap = interactorsMap;
    }

    public DatabaseInfo getDatabaseInfo() {
        return databaseInfo;
    }

    /**
     * Returns a clone of the clean version of the hierarchies
     *
     * @return a clone of the clean version of the hierarchies
     */
    public HierarchiesData getHierarchiesData() {
        //The object is not kept by itself because it requires more disk space
        HierarchiesData data = new HierarchiesData(this.pathwayHierarchies, this.pathwayLocation);
        return AnalysisDataUtils.kryoCopy(data);
    }

    public EntitiesContainer getEntitiesContainer() {
        return entitiesContainer;
    }

    public IdentifiersMap<EntityNode> getEntitiesMap() {
        return entitiesMap;
    }

    public IdentifiersMap<InteractorNode> getInteractorsMap() {
        return interactorsMap;
    }

    public void initialize() {
        this.entitiesContainer.setOrthologiesCrossLinks();
    }

}