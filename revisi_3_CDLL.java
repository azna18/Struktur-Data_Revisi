import java.util.Scanner;

public class revisiCDLL {

    static class Node {
        String berita;
        Node next, prev;

        Node(String berita) {
            this.berita = berita;
        }
    }

    static Node head = null;
    static int size = 0;

    // 1. Insert di akhir
    static void insert(String berita) {
        Node newNode = new Node(berita);

        if (head == null) {
            head = newNode;
            head.next = head;
            head.prev = head;
        } else {
            Node tail = head.prev;

            tail.next = newNode;
            newNode.prev = tail;
            newNode.next = head;
            head.prev = newNode;
        }

        size++;
        System.out.println("Berita berhasil ditambahkan!");
    }

    // 2. Delete berdasarkan posisi
    static void delete(int posisi) {
        if (head == null || posisi < 1 || posisi > size) {
            System.out.println("Posisi tidak valid!");
            return;
        }

        Node temp = head;

        if (size == 1) {
            head = null;
        } else {
            for (int i = 1; i < posisi; i++) {
                temp = temp.next;
            }

            temp.prev.next = temp.next;
            temp.next.prev = temp.prev;

            if (temp == head) {
                head = temp.next;
            }
        }

        size--;
        System.out.println("Berita ke-" + posisi + " berhasil dihapus!");
    }

    // 3. Tampil Forward - loop terus (circular), stop saat Enter
    static void tampilForward() {
        if (head == null) {
            System.out.println("Belum ada berita!");
            return;
        }

        System.out.println("Tekan Enter untuk menghentikan tampilan...");

        // Thread terpisah untuk deteksi input Enter dari user
        final boolean[] berjalan = {true};
        Thread inputThread = new Thread(() -> {
            try {
                System.in.read();
                berjalan[0] = false;
            } catch (Exception e) {
                berjalan[0] = false;
            }
        });
        inputThread.setDaemon(true);
        inputThread.start();

        Node temp = head;
        int no = 1;

        // Loop terus memanfaatkan circular — tidak ada kondisi berhenti selain flag
        while (berjalan[0]) {
            System.out.println(no + ". " + temp.berita);

            temp = temp.next; // karena circular, setelah tail otomatis kembali ke head
            no++;

            // Reset nomor saat kembali ke head
            if (temp == head) {
                no = 1;
            }

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                break;
            }
        }

        System.out.println("Tampilan dihentikan.");
    }

    // 4. Tampil Backward - loop terus (circular), stop saat Enter
    static void tampilBackward() {
        if (head == null) {
            System.out.println("Belum ada berita!");
            return;
        }

        System.out.println("Tekan Enter untuk menghentikan tampilan...");

        // Thread terpisah untuk deteksi input Enter dari user
        final boolean[] berjalan = {true};
        Thread inputThread = new Thread(() -> {
            try {
                System.in.read();
                berjalan[0] = false;
            } catch (Exception e) {
                berjalan[0] = false;
            }
        });
        inputThread.setDaemon(true);
        inputThread.start();

        Node temp = head.prev; // mulai dari tail
        int no = size;

        // Loop terus memanfaatkan circular — tidak ada kondisi berhenti selain flag
        while (berjalan[0]) {
            System.out.println(no + ". " + temp.berita);

            temp = temp.prev; // karena circular, setelah head otomatis kembali ke tail
            no--;

            // Reset nomor saat kembali ke tail
            if (temp == head.prev) {
                no = size;
                System.out.println("--- Kembali ke berita terakhir ---");
            }

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                break;
            }
        }

        System.out.println("Tampilan dihentikan.");
    }

    // 5. Tampil berita tertentu
    static void tampilTertentu(int posisi) {
        if (head == null || posisi < 1 || posisi > size) {
            System.out.println("Posisi tidak valid!");
            return;
        }

        Node temp = head;
        for (int i = 1; i < posisi; i++) {
            temp = temp.next;
        }

        System.out.println("Berita ke-" + posisi + ": " + temp.berita);
    }

    // 6. Tampil semua berita (tanpa delay)
    static void tampilSemua() {
        if (head == null) {
            System.out.println("Belum ada berita!");
            return;
        }

        System.out.println("\n--- Daftar Semua Berita (" + size + " berita) ---");
        Node temp = head;
        int no = 1;

        do {
            System.out.println(no + ". " + temp.berita);
            temp = temp.next;
            no++;
        } while (temp != head);
    }

    // MAIN PROGRAM
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int pilihan;

        do {
            System.out.println("\n===== MENU TEKS BERJALAN (CDLL) =====");
            System.out.println("1. Insert berita");
            System.out.println("2. Hapus berita");
            System.out.println("3. Tampilkan Forward");
            System.out.println("4. Tampilkan Backward");
            System.out.println("5. Tampil berita tertentu");
            System.out.println("6. Tampil semua berita");
            System.out.println("7. Exit");
            System.out.print("Pilih menu: ");
            pilihan = input.nextInt();
            input.nextLine();

            switch (pilihan) {
                case 1:
                    System.out.print("Masukkan teks berita: ");
                    String berita = input.nextLine();
                    insert(berita);
                    break;

                case 2:
                    tampilSemua();
                    System.out.print("Masukkan nomor berita yang dihapus: ");
                    int hapus = input.nextInt();
                    delete(hapus);
                    break;

                case 3:
                    tampilForward();
                    input.nextLine(); // bersihkan buffer setelah stop
                    break;

                case 4:
                    tampilBackward();
                    input.nextLine(); // bersihkan buffer setelah stop
                    break;

                case 5:
                    tampilSemua();
                    System.out.print("Masukkan nomor berita: ");
                    int lihat = input.nextInt();
                    tampilTertentu(lihat);
                    break;

                case 6:
                    tampilSemua();
                    break;

                case 7:
                    System.out.println("Program selesai.");
                    break;

                default:
                    System.out.println("Pilihan tidak valid!");
            }

        } while (pilihan != 7);

        input.close();
    }
}
