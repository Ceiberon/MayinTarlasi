import java.util.Random;
import java.util.Scanner;

public class MineSweeper { //Değerlendirme formu 6: oyun MineSwepper kurucu metot içerisinde tanımlandı
    Scanner scanner = new Scanner(System.in);
    int toplamSatir, toplamSutun, kullaniciSutun, kullaniciSatir;
    String[][] kullaniciMayinTarlasi;
    String[][] yoneticiMayinTarlasi;

    public int toplamSatirAl() {
        int toplamSatir;
        while (true) {
            System.out.println("Satır Sayısını giriniz :");
            toplamSatir = scanner.nextInt();
            if (toplamSatir >= 2) {
                break;
            }
            System.out.println("Hatalı bir sayı girdiniz");
        }
        return toplamSatir;
    }

    public int toplamSutunAl() {
        Scanner scanner = new Scanner(System.in);
        int toplamSutun;
        while (true) {
            System.out.println("Sutun Sayısını giriniz :");
            toplamSutun = scanner.nextInt();
            if (toplamSutun >= 2) {
                break;
            }
            System.out.println("Hatalı bir sayı girdiniz");
        }
        return toplamSutun;
    }

    public void mayinTarlasiOlustur() {
        kullaniciMayinTarlasi = new String[toplamSatir][toplamSutun];
        yoneticiMayinTarlasi = new String[toplamSatir][toplamSutun];
        for (int i = 0; i < toplamSatir; i++) {
            for (int j = 0; j < toplamSutun; j++) {
                kullaniciMayinTarlasi[i][j] = "-";
                yoneticiMayinTarlasi[i][j] = "-";
            }
        }
    }

    public void tarlaYazdir(String[][] tarla) {
        for (int i = 0; i < toplamSatir; i++) {
            for (int j = 0; j < toplamSutun; j++) {

                System.out.print(tarla[i][j]);
            }
            System.out.println();
        }
    }

    public void mayinKoy() {  // Madde 8: Mayınlar rastgele ve 1/4 oranında dağıtıldı

        int toplamMayinSayisi = (toplamSatir * toplamSutun) / 4;
        int mevcutMayinSayisi = 0;
        Random rand = new Random();
        while (true) {
            int mayinSatir = rand.nextInt(toplamSatir);
            int mayinSutun = rand.nextInt(toplamSutun);

            if (yoneticiMayinTarlasi[mayinSatir][mayinSutun] == "*") {
                continue;
            }
            yoneticiMayinTarlasi[mayinSatir][mayinSutun] = "*";
            mevcutMayinSayisi++;
            if (toplamMayinSayisi == mevcutMayinSayisi) {
                break;
            }
        }
    }

    public void oyunBaslat() {  // Madde 13,14 ve 15 Kullanıcının kazanıp kaybetme durumunu ve kontrolünü sağlıyor
        int kazanmaKosulu = (toplamSatir * toplamSutun) - (toplamSatir * toplamSutun) / 4;
        int mevcutGiriş = 0;
        while (mevcutGiriş < kazanmaKosulu) {
            satirSutunAl();
            if (yoneticiMayinTarlasi[kullaniciSatir][kullaniciSutun].equals("*")) {
                System.out.println("KAYBETTİNİZ");
                break;
            }
            int[][] uzaklikMatrisi = uzaklikHesapla();
            int mayinSayisi = mayinSay(uzaklikMatrisi);

            kullaniciMayinTarlasi[kullaniciSatir][kullaniciSutun] = Integer.toString(mayinSayisi);
            tarlaYazdir(kullaniciMayinTarlasi);

            mevcutGiriş++;
        }
        if (mevcutGiriş == kazanmaKosulu) {
            System.out.println("Tebrikler Kazandın!");
        }
    }

    public void satirSutunAl() {  //Madde 9: Kullanıcıdan açılmasını istediğin satır ve sutun bilgisini alır
        while (true) {
            System.out.print("Satır giriniz : ");
            kullaniciSatir = scanner.nextInt();
            if (kullaniciSatir >= toplamSatir || kullaniciSatir < 0) {
                System.out.println("Hatalı girdiniz");
                continue;
            }
            System.out.print("Sutun giriniz : ");
            kullaniciSutun = scanner.nextInt();
            if (kullaniciSutun >= toplamSatir || kullaniciSutun < 0) {
                System.out.println("Hatalı girdiniz");
                continue;
            }
            break;
        }
    }

    public int[][] uzaklikHesapla() {  // Madde 12: Açılan alana göre çevredeki mayın sayısını verir
        int[][] uzaklikMatrisi = new int[toplamSatir][toplamSutun];

        for (int i = 0; i < toplamSatir; i++) {
            for (int j = 0; j < toplamSutun; j++) {
                int satirFarki = kullaniciSatir - i, sutunFarki = kullaniciSutun - j;
                int kareFarkToplami = (int) (Math.pow(satirFarki, 2) + Math.pow(sutunFarki, 2));
                int uzaklik = (int) Math.sqrt(kareFarkToplami);
                uzaklikMatrisi[i][j] = uzaklik;
            }
        }
        return uzaklikMatrisi;
    }

    public int mayinSay(int[][] uzaklikMatrisi) {
        int mayinSayisi = 0;

        for (int i = 0; i < toplamSatir; i++) {
            for (int j = 0; j < toplamSutun; j++) {
                if (uzaklikMatrisi[i][j] == 1 && yoneticiMayinTarlasi[i][j] == "*") {
                    mayinSayisi++;
                }
            }
        }
        return mayinSayisi;
    }
    public MineSweeper() {
        toplamSatir = toplamSatirAl();
        toplamSutun = toplamSutunAl();
        System.out.println(toplamSatir);
        System.out.println(toplamSutun);
        mayinTarlasiOlustur();
        tarlaYazdir(kullaniciMayinTarlasi);
        System.out.println("///////////////// Oyun başladı bol şans ///////////////// ");
        mayinKoy();
        tarlaYazdir(yoneticiMayinTarlasi);
        oyunBaslat();
    }
}
