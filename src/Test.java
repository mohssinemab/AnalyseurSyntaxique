public class Test {

    public static void main(String []args){
        Grammaire grammaire = new Grammaire(" main ( ) {  int id int id }");
//      Chaine acceptée    main ( ) {  int id int id }
//      Chaine refusée    main ( ) {  float id = nombre }

        grammaire.analyser();
    }
}
