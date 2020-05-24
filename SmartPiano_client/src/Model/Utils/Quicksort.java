package Model.Utils;


import Model.Song_database;

public class Quicksort {
    /**
     * Recursive method for sorting elements in a given array.
     * @param a The given array.
     * @param i The initial index of the array/subpartition.
     * @param j The final index of the array/subpartition.
     * @return The array sorted.
     */
    public static Song_database[] Quicksort(Song_database[] a, int i, int j){
        int s, t;
        Returned returned;
        if(i > j || i == j){
            return a;
        } else {
            returned = particio(a, i, j);
            a = returned.getA();
            s = returned.getS();
            t = returned.getT();
            a = Quicksort(a, i, t);
            a = Quicksort(a, s, j);
        }
        return a;
    }

    /**
     * Method that creates a partition in the array. Method used only by the QuickSort.
     * @param a Given array/subpartition.
     * @param i Initial index.
     * @param j Final index.
     * @return A class made to return three different objects: the array and the new initial and final indexes.
     */
    private static Returned particio (Song_database[] a, int i, int j){
        Returned returnedTeams = new Returned(a, i, j);
        int mig;
        Song_database tmp;
        double pivot;

        returnedTeams.setS(i);
        returnedTeams.setT(j);
        mig = (i+j)/2;

        pivot = a[mig].getNum_reproductions();

        while(returnedTeams.getS() < returnedTeams.getT() || returnedTeams.getS() == returnedTeams.getT()){
            while(a[returnedTeams.getS()].getNum_reproductions() < pivot){
                returnedTeams.setS(returnedTeams.getS()+1);
            }
            while((a[returnedTeams.getT()].getNum_reproductions() > pivot)){
                returnedTeams.setT(returnedTeams.getT()-1);
            }
            if(returnedTeams.getS() < returnedTeams.getT()){
                tmp = (a[returnedTeams.getS()]);

                a[returnedTeams.getS()] = a[returnedTeams.getT()];
                a[returnedTeams.getT()] = tmp;
                returnedTeams.setA(a);
                returnedTeams.setS(returnedTeams.getS()+1);
                returnedTeams.setT(returnedTeams.getT()-1);
            } else {
                if(returnedTeams.getS() == returnedTeams.getT()){
                    returnedTeams.setS(returnedTeams.getS()+1);
                    returnedTeams.setT(returnedTeams.getT()-1);
                }
            }
        }
        return returnedTeams;
    }
}


