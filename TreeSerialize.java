import java.util.List;
import java.util.Queue;
import java.util.ArrayList;
import java.util.LinkedList;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

class Node {
    char data;
    Node leftChild, rightChild;

    public Node() {

    }

    public Node(char data) {
        this.data = data;
        leftChild = rightChild = null;
    }
}

class TreeSerialize {

    // a function to insert node in a tree
    public static Node insertNode(Node root, char key) {

        if (root == null) {
            Node temp = new Node(key);
            root = temp;
            return root;
        } else {
            if (root.leftChild == null) {
                root.leftChild = insertNode(root.leftChild, key);
            } else
                root.rightChild = insertNode(root.rightChild, key);
        }
        return root;
    }

    // a funtion to print levelOrderTree traversal
    public static void printLevelOrder(Node root) {
        Queue<Node> queue = new LinkedList<Node>();
        queue.add(root);
        while (!queue.isEmpty()) {

            Node tempNode = queue.poll();
            System.out.print(tempNode.data);

            /* Enqueue left child */
            if (tempNode.leftChild != null) {
                queue.add(tempNode.leftChild);
            }

            /* Enqueue right child */
            if (tempNode.rightChild != null) {
                queue.add(tempNode.rightChild);
            }
        }
    }

    public static void main(String[] args) {
        String option = args[0];
        String csvFileInput = "";
        String csvFileOutput = "";
        // String csvFileInput = args[1];
        // String csvFileOutput = args[2];
        if (args.length == 3) {
            csvFileInput = args[1];
            csvFileOutput = args[2];
        } else if (args.length == 2) {
            csvFileOutput = args[1];
        }

        BufferedReader br = null;
        String line = "";
        Node root = null;

        List<String> lines = new ArrayList<String>();
        List<Integer> length = new ArrayList<Integer>();
        ArrayList<Character> list = new ArrayList<Character>();

        try {
            // if uses choses to serialize the file
            if (option.equals("create")) {

                // reading contents from csv file
                br = new BufferedReader(new FileReader(csvFileInput));
                while ((line = br.readLine()) != null) {
                    String ar[] = line.split(";");
                    ar[0] += '\n';
                    lines.add(ar[0]);
                }

                // creating a string of file contents
                String str = "";
                for (int i = 0; i < lines.size(); i++) {
                    str += lines.get(i);
                }

                // creating a binary tree for file contents
                root = null;
                for (int i = 0; i < str.length(); i++) {
                    root = insertNode(root, str.charAt(i));
                }

                // serializing file contents
                serialize(root, list);

                // putting the serialized data to a file
                OutputStream outputStream = new FileOutputStream(csvFileOutput);
                String a = "";
                for (int i = 0; i < list.size(); i++) {
                    a += list.get(i);
                }
                byte[] allBytes = new String(a).getBytes(StandardCharsets.UTF_8);
                outputStream.write(allBytes);

                // if user choses to deserialize the data
            } else if (option.equals("load")) {
                try {

                    // reading serialized data from the file
                    InputStream inputStream = new FileInputStream(csvFileOutput);
                    int byteRead;
                    list.clear();
                    while ((byteRead = inputStream.read()) != -1) {

                        list.add((char) byteRead);
                    }
                    // deserializing the file
                    root = deSerialize(list);
                    // level order file traversal
                    printLevelOrder(root);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (br != null) {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void serialize(Node root, ArrayList<Character> list) {
        if (root == null) {
            list.add('@');
            return;
        }
        list.add(root.data);

        serialize(root.leftChild, list);
        serialize(root.rightChild, list);
    }

    static int deSerializeIndex;

    public static Node deSerialize(ArrayList<Character> list) {
        if (list.get(deSerializeIndex) == '@') {
            deSerializeIndex++;
            return null;
        }
        Node newNode = new Node(list.get(deSerializeIndex++));
        newNode.leftChild = deSerialize(list);
        newNode.rightChild = deSerialize(list);

        return newNode;
    }
}