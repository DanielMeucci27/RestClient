import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;



public class RestClient {

    private static final String URL  = "http://localhost:8080/api/tutorial/1.0/employees";

    public static void main (String args[]){

        boolean continueLoop = true;

        while(continueLoop == true){
            BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("\n1: GET \n2: POST \n3: PUT \n4: GET{id} \n5: DELETE{id} \n0: STOP PROGRAM \nInsert number...: ");
            try {
                String riga = console.readLine();
                switch (Integer.parseInt(riga)){
                    case 1: {
                        getEmployees();
                        break;
                    }
                    case 2: {
                        postEmployee(console);
                        break;
                    }
                    case 3: {
                        putEmployee(console);
                        break;
                    }
                    case 4: {
                        getEmployee(console);
                        break;
                    }
                    case 5: {
                        deleteEmployee(console);
                        break;
                    }
                    case 0: {
                        continueLoop = false;
                        break;
                    }
                    default: break;
                }
            } catch (Exception e){
            }
        }
    }

    private static HttpURLConnection initConnection(String id, String method, String accept) throws Exception{

        URL url;
        if (id != "") {
            url = new URL(URL+ "/" + id);
        } else
            url = new URL(URL);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod(method);
        if (method.equals("GET") || method.equals("DELETE"))
            conn.setRequestProperty("Accept", accept);
        else
            conn.setRequestProperty("Content-Type", accept);

        return conn;
    }

    private static String getJSON(BufferedReader console) throws Exception{
        String input = "";

        System.out.print("Id impiegato: ");
        String idEmp = console.readLine();
        System.out.print("Nome impiegato: ");
        String firstName = console.readLine();
        System.out.print("Cognome impiegato: ");
        String lastName = console.readLine();
        System.out.print("Email impiegato: ");
        String email = console.readLine();
        System.out.print("Telefono impiegato: ");
        String phone = console.readLine();

        input = "{\"employeeId\":\"" + idEmp + "\"," +
                "\"firstName\":\"" + firstName +"\"," +
                "\"lastName\":\"" + lastName + "\"," +
                "\"email\":\"" + email + "\"," +
                "\"phone\":\"" + phone + "\"" +
                "}";

        return input;
    }

    /**
     * Function which sends a GET request to the webservice to retrieve all the employees
     */
    private static void getEmployees(){
        try {

            HttpURLConnection conn = initConnection("", "GET", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String output;
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }
            conn.disconnect();

        } catch (MalformedURLException e) {
            e.getMessage();
        } catch (IOException e) {
            e.getMessage();
        } catch (Exception e) {
            e.getMessage();
        }
    }

    private static void postEmployee(BufferedReader console){
        try {

            HttpURLConnection conn = initConnection("", "POST", "application/json");

            OutputStream os = conn.getOutputStream();

            String input = getJSON(console);

            os.write(input.getBytes());
            os.flush();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
                System.out.println("Failed : HTTP error code : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String output;
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }

            conn.disconnect();
        } catch (MalformedURLException e) {
            e.getMessage();
        } catch (IOException e) {
            e.getMessage();
        } catch (Exception e) {
            e.getMessage();
        }
    }

    private static void getEmployee(BufferedReader console) {
        try {
            System.out.print("Id Impiegato: ");
            String id = "";
            id = console.readLine();
            HttpURLConnection conn = initConnection(id, "GET", "application/json");

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                System.out.println("Failed : HTTP error code : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String output;
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }
            conn.disconnect();

        } catch (MalformedURLException e) {
            e.getMessage();
        } catch (IOException e) {
            e.getMessage();
        } catch (Exception e) {
            e.getMessage();
        }
    }

    private static void putEmployee(BufferedReader console){
        try {
            System.out.print("Id Impiegato desiderato: ");
            String id = "";
            id = console.readLine();
            HttpURLConnection conn = initConnection(id, "PUT", "application/json");

            OutputStream os = conn.getOutputStream();

            String input = getJSON(console);
            os.write(input.getBytes());
            os.flush();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                System.out.println("Failed : HTTP error code : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String output;
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }

            conn.disconnect();
        } catch (MalformedURLException e) {
            e.getMessage();
        } catch (IOException e) {
            e.getMessage();
        } catch (Exception e) {
            e.getMessage();
        }
    }

    private static void deleteEmployee(BufferedReader console) {
        try {
            System.out.print("Id Impiegato desiderato: ");
            String id = "";
            id = console.readLine();
            HttpURLConnection conn = initConnection(id, "DELETE", "application/json");

            if (conn.getResponseCode() != 200) {
                System.out.println("Failed : HTTP error code : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String output;
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }
            conn.disconnect();

        } catch (MalformedURLException e) {
            e.getMessage();
        } catch (IOException e) {
            e.getMessage();
        } catch (Exception e) {
            e.getMessage();
        }
    }
}


