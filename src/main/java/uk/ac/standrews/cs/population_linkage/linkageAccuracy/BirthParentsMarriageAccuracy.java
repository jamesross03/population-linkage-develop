/*
 * Copyright 2022 Systems Research Group, University of St Andrews:
 * <https://github.com/stacs-srg>
 *
 * This file is part of the module population-linkage.
 *
 * population-linkage is free software: you can redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * population-linkage is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with population-linkage. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package uk.ac.standrews.cs.population_linkage.linkageAccuracy;

import uk.ac.standrews.cs.neoStorr.util.NeoDbCypherBridge;

public class BirthParentsMarriageAccuracy extends AbstractAccuracy {

    private static final String BIRTH_PARENTS_MARRIAGE_TPC = "MATCH (b:Birth)-[r:ID {actors: \"Child-Mother\"}]-(m:Marriage), (b)-[:ID {actors: \"Child-Father\"}]-(m) WHERE (b)-[:GT_ID {actors: \"Child-Couple\"}]-(m) AND NOT (b)-[:DELETED]-(m) return count(r)";
    private static final String BIRTH_PARENTS_MARRIAGE_FPC = "MATCH (b:Birth)-[r:ID {actors: \"Child-Mother\"}]-(m:Marriage), (b)-[:ID {actors: \"Child-Father\"}]-(m) WHERE NOT (b)-[:GT_ID {actors: \"Child-Couple\"}]-(m) AND NOT (b)-[:DELETED]-(m) return count(r)";
    private static final String BIRTH_PARENTS_MARRIAGE_FNC = "MATCH (b:Birth)-[r:GT_ID {actors: \"Child-Couple\"}]-(m:Marriage) WHERE NOT (b)-[:ID {actors: \"Child-Mother\"}]-(m) and NOT (b)-[:ID {actors: \"Child-Father\"}]-(m) OR (b)-[:DELETED]-(m)  return count(r)";

    // FATHER
    // MOTHER
    // don't know which

    public BirthParentsMarriageAccuracy(NeoDbCypherBridge bridge) {
        super(bridge);
        doqueries();
    }

    private void doqueries() {
        long tpc = doQuery(BIRTH_PARENTS_MARRIAGE_TPC);
        long fpc = doQuery(BIRTH_PARENTS_MARRIAGE_FPC);
        long fnc = doQuery(BIRTH_PARENTS_MARRIAGE_FNC);

        long birth_count = doQuery( ALL_BIRTHS );
        long marriage_count = doQuery( ALL_MARRIAGES );
        long all_pair_count = birth_count*marriage_count;

        report(fpc,tpc,fnc,all_pair_count);
    }

    public static void main(String[] args) {
        try (NeoDbCypherBridge bridge = new NeoDbCypherBridge()) {
            BirthParentsMarriageAccuracy acc = new BirthParentsMarriageAccuracy(bridge);
        }
    }
}
