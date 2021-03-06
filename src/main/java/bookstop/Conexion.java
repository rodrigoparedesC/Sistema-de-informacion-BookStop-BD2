package bookstop;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;


import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;



public class Conexion {

    private final String uri = "mongodb+srv://bookstop_dev:bookstop123@bookstop.vyba2.mongodb.net/?retryWrites=true&w=majority";
    
    private ConnectionString connectionString = new ConnectionString(uri);
    private CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
    private CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);
    private  MongoClientSettings clientSettings = MongoClientSettings.builder()
                                                            .applyConnectionString(connectionString)
                                                            .codecRegistry(codecRegistry)
                                                            .build();
    private MongoClient mongoClient = MongoClients.create(clientSettings);
    public MongoDatabase database = mongoClient.getDatabase("Bookstop");
    MongoCollection<Libros> collection_Libros;
    MongoCollection<Clientes> collection_Clientes;

    public Conexion(String colecction){
        
        if(colecction.equals("Libros")){
            collection_Libros = database.getCollection(colecction,Libros.class);
           
        }
        if(colecction.equals("Clientes")){
            collection_Clientes =  database.getCollection("Clientes",Clientes.class);
        }
            

    }

    public ArrayList<String> getstringValues(String value){

        ArrayList<String> ArrayList_values = new ArrayList<>();
        MongoCursor<Libros> cursor = collection_Libros.find().iterator();
        while(cursor.hasNext()){
        Libros libro = cursor.next();
        if(libro != null){
            if(value.equals("Titulo"))
                ArrayList_values.add(libro.getTitulo());
            if(value.equals("Autor"))
                ArrayList_values.add(libro.getNombre_del_autor());
            if(value.equals("Editorial"))
                ArrayList_values.add(libro.getEditorial());
            if(value.equals("Genero"))
                ArrayList_values.add(libro.getTipo_genero());
            if(value.equals("Subgenero"))
                ArrayList_values.add(libro.getTipo_subgenero());
        }
    } 
    
    if(value.equals("Titulo")){
        return ArrayList_values;
    }else{  //Encargado de eliminar repetidos
        Set<String> set = new HashSet<>(ArrayList_values);
        ArrayList_values.clear();          
        ArrayList_values.addAll(set);
        return ArrayList_values;
    }
    
}
    
public ArrayList<String> getintegerValues (String value) {
    
    ArrayList<String> ArrayList_values = new ArrayList<>();
    MongoCursor<Libros> cursor = collection_Libros.find().iterator();
    while(cursor.hasNext()){
        Libros libro = cursor.next();
        if(libro != null){
            ArrayList_values.add(String.valueOf(libro.getPrecio()));
        }
    } 

    return ArrayList_values;

    }

    public ArrayList<String> getClienteValues(String value){

        ArrayList<String> ArrayList_values = new ArrayList<>();
        MongoCursor<Clientes> cursor = collection_Clientes.find().iterator();
        while(cursor.hasNext()){
        Clientes cliente = cursor.next();
        if(cliente != null){
            if(value.equals("Nombre"))
                ArrayList_values.add(cliente.getNombre());
            if(value.equals("RUT"))
                ArrayList_values.add(String.valueOf(cliente.getRut()));
            /*if(value.equals("ID boleta"))
                ArrayList_values.add(String.valueOf(cliente.get()));*/
           /*  if(value.equals("Fecha"))
                ArrayList_values.add(String.valueOf(cliente.getRut()));*/
        }
    } 
    
        return ArrayList_values;
    
}


    
}
