import java.sql.Struct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Grammaire {
    //Tableau des symboles Non Terminaux
    public static ArrayList<String>Vn = new ArrayList<String>(
            Arrays.asList("<Programme>","<liste_declarations>","<une_declaration>","<liste_instructions>","<une_instruction>","<type>","<affectation>","<test>","<condition>","<operateur>")
    );
    //Tableau des symboles Terminaux
    public static ArrayList<String>Vt = new ArrayList<String>(
            Arrays.asList("main","(",")","{","}","id","int","float","=","nombre","if","else",";","<",">","$")
    );

    //Table d'analyse
    public static ArrayList<ArrayList<String>> tabAnalyse = new ArrayList<ArrayList<String>>();

    //Remplissage de la table d'analyse
    public void initialiseTable(){

        tabAnalyse.add(0, new ArrayList<>(
                Arrays.asList("main ( ) { <liste_declarations> <liste_instructions> }",null,null,null,null,null,null,null,null,null,null,null,null,null,null,null)
        ));
        tabAnalyse.add(1, new ArrayList<>(
                Arrays.asList(null,null,null,null,"vide","vide","<une_declaration> <liste_declarations>","<une_declaration> <liste_declarations>",null,null,"vide",null,null,null,null,"vide")
        ));
        tabAnalyse.add(2, new ArrayList<>(
                Arrays.asList(null,null,null,null,null,null,"<type> id","<type> id",null,null,null,null,null,null,null,null)
        ));
        tabAnalyse.add(3, new ArrayList<>(
                Arrays.asList(null,null,null,null,"vide","<une_instruction> <liste_instructions>",null,null,null,null,"<une_instruction> <liste_instructions>",null,null,null,null,"vide")
        ));
        tabAnalyse.add(4, new ArrayList<>(
                Arrays.asList(null,null,null,null,null,"<affectation>",null,null,null,null,"<test>",null,null,null,null,null)
        ));
        tabAnalyse.add(5, new ArrayList<>(
                Arrays.asList(null,null,null,null,null,null,"int","float",null,null,null,null,null,null,null,null)
        ));
        tabAnalyse.add(6, new ArrayList<>(
                Arrays.asList(null,null,null,null,null,"id = nombre ;",null,null,null,null,null,null,null,null,null,null)
        ));
        tabAnalyse.add(7, new ArrayList<>(
                Arrays.asList(null,null,null,null,null,null,null,null,null,null,"if <condition> <une_instruction> else <une_instruction> ;",null,null,null,null,null)
        ));
        tabAnalyse.add(8, new ArrayList<>(
                Arrays.asList(null,null,null,null,null,"id <operateur> nombre",null,null,null,null,null,null,null,null,null,null)
        ));
        tabAnalyse.add(9, new ArrayList<>(
                Arrays.asList(null,null,null,null,null,null,null,null,"=",null,null,null,null,"<",">",null)
        ));

    }


    //La chaine inseree
    public ArrayList<String> inserted = new ArrayList<String>();

    public Grammaire(String str)
    {
        this.initialiseTable();

        //split de la chaine inseree
        String[] splitStr = str.trim().split("\\s+");
        int i=splitStr.length-1;
        //la chaine est inseree du right to left
        inserted.add("$");
        while (i>=0){
            inserted.add(splitStr[i]);
            i--;
        }
    }


    public ArrayList<String> diviser (String s){
        ArrayList<String> t= new ArrayList<String>();
        String[] splitStr = s.trim().split("\\s+");
        int i=splitStr.length-1;
        //on l'ajoute au tableau du right to left
        while (i>=0){
            t.add(splitStr[i]);
            i--;
        }
        return t;

    }


    //Analyse ascendante
    public void analyser(){
        ArrayList<String> pile = new ArrayList<String>(Arrays.asList("$","<Programme>"));
        int ps; // pile sommet
        int es; // entree sommet
        while (true) {
            ps = pile.size() - 1;
            System.out.println("Pile : "+pile+" --sommet : "+pile.get(ps));
            es = inserted.size() - 1;
            System.out.println("Insterted : "+inserted+" --sommet : "+inserted.get(es));

            if (pile.get(ps)=="$" && inserted.get(es) =="$" ){
                System.out.println(" $ < =`> $ ");
                System.out.println(" -------------Chaine acceptée !!");
                break;

            }else if (pile.get(ps).equals(inserted.get(es)) ) {

                //les sommets sont les memes donc on les depile
                System.out.println("Les deux depilés ");
                inserted.remove(es);
                pile.remove(ps);

            }else{

                //Si les deux sommets terminaux mais differents
                if( Vt.contains(pile.get(ps)) &&  Vt.contains(inserted.get(es)) &&   !( pile.get(ps).equals(inserted.get(es)) )  ){
                    System.out.println(" -------------Erreur de Syntaxe !!");
                    break;

                }
                //diffrents (Vt et Vn) donc on remplace le sommet de la pile avec la production

                // on trouve le sommet du pile
                int index1 = Vn.indexOf(pile.get(ps));
                // on trouve le sommet de l'entree
                int index2 = Vt.indexOf(inserted.get(es));

                //on cherche la production
                String production = tabAnalyse.get(index1).get(index2);
                System.out.println("Leur production  = "+production);
                //si on a pas trouvee une prod donc chaine refusee
                if(production==null){
                    System.out.println(" -------------Chaine refusee !!");
                    break;
                }

                ArrayList<String> productionArr = this.diviser(production);
                System.out.println("Array production : "+productionArr);
                //si la prod est le vide on depile le sommet de la pile
                if(productionArr.get(0)=="vide"){
                    System.out.println("Le sommet du pile est dépilé");
                    pile.remove(ps);

                }else{
                    //on empile la production(array) au sommet de la pile

                    pile.remove(ps);
                    pile.addAll(productionArr);
                    System.out.println("La prodcution est émpilé - Apres l'empilage : "+pile);

                }

            }

        }


    }


}
