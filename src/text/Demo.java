package text;

public class Demo {
    public static void main(String[] args) throws InterruptedException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(char i = 'a'; i <= 'z'; i++){
                    System.out.println(i);
                }
            }
        }).start();
        for(int i = 0; i < 10; i++){
            if(i == 5){
                Thread.sleep(5000);
            }
            System.out.println(i);
        }
    }
}
