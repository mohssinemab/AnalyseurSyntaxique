// MOHSSINE AIT BOUKDIR
// ZAKARIA AJLI

public class Test {

    public static void main(String []args){
        //on passe en argument la chaine a verifier
        Grammaire grammaire = new Grammaire(" main ( ) {  int id int id }");

        //lancement de la fonction d'analyse
        grammaire.analyser();
    }

    //      Chaine acceptée    main ( ) {  int id int id }
//      Chaine refusée    main ( ) {  float id = nombre }
}
