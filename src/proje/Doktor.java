package proje;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.PosixFilePermission;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Doktor {
	int girdiSayisi = 0;
	int veriSayisi = 0;
	boolean bool = false;
	String[] satirlar;
	double momentum = 0.8;
	double n = 0.9;

	public double[][] girdiler;// girdilerim
	public double[][] noronlar;
	public double[][] agirliklarGirdi;
	public double[][] agirlikGirdiFark;
	public double[] agirlikCikisFark;
	public double[] agirliklarCikis;
	int gizliKatmanNoronSayisi = 5;
	public double[] max;
	public double[] min;
	double minHedef;
	double maxHedef;
	List araKatmanNoronlari;
	double[] hedef;
	String modifiyelidegerler = "degerler2_tmp.txt";
	BufferedWriter bw_olustur;

	// constructor
	public Doktor() throws IOException {

		araKatmanNoronlari = new ArrayList();

		// double[] noron3 = { 3, 3, 2, 1, 4, 4, 6, 5, 4, 3, 2, 1, 6, 6, 4, 3,
		// 2, 1, 4, 5, 3, 2, 1, 3, 5, 12 };
		// bw_olustur = dosyaOlustur(modifiyelidegerler);

		// bool = GirisNoronEkle(noron3);
		if (!bool)
			modifiyelidegerler = "degerler123.txt";
		girdiSayisi = getirSatirSayisi();// satir sayisi
		hedef = new double[girdiSayisi];

		// veriSayisi=sutunSatirSayisi();
		satirlar = new String[girdiSayisi];

		try {
			olusturGirdiler();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		veriSayisi = getirSutunSayisi();// sütun sayisi
		System.out.println("girdisayisi:" + girdiSayisi + " " + veriSayisi);

		max = new double[veriSayisi - 1];// -1 olmasının nedeni son sutun yani
											// hedef olmayacak
		min = new double[veriSayisi - 1];
		girdiler = new double[girdiSayisi][veriSayisi - 1];
		noronlar = new double[veriSayisi - 1][girdiSayisi];

		noronlariDoldur();
		for (int i = 0; i < girdiler.length; i++) {

			for (int j = 0; j < girdiler[i].length; j++) {

				System.out.print(girdiler[i][j] + " ");

			}

			System.out.println(hedef[i] + " ");

		}

		System.out.println(" ");

		for (int i = 0; i < girdiler[0].length; i++) {
			for (int j = 0; j < girdiler.length; j++) {
				noronlar[i][j] = girdiler[j][i];
				System.out.print(noronlar[i][j] + " ");
			}

			System.out.println(" ");

		}
		System.out.println("");
		for (int i = 0; i < noronlar.length; i++) {
			max[i] = maxBul(noronlar[i]);
			min[i] = minBul(noronlar[i]);
			for (int j = 0; j < noronlar[0].length; j++) {
				noronlar[i][j] = 0.8 * (noronlar[i][j] - min[i]) / (max[i] - min[i]);
				System.out.print(noronlar[i][j] + " ");
			}
			// SUTUN SAYİSİ.HOCA YENI BIR NORON
			// EKLEMEK ISTERSE BURDAN
			// AYARLANACAK
			System.out.println("");
		}
		System.out.println("");
		System.out.println("");

		minHedef = minBul(hedef);
		maxHedef = maxBul(hedef);
		for (int i = 0; i < hedef.length; i++) {
			hedef[i] = 0.8 * (hedef[i] - minHedef) / (maxHedef - minHedef);
		}

		for (int i = 0; i < noronlar[0].length; i++) {
			for (int j = 0; j < noronlar.length; j++) {
				girdiler[i][j] = noronlar[j][i];
				System.out.print(girdiler[i][j] + " ");

			}
			System.out.println("");

		}
		agirlikDoldur();
		int[] diziEgitim = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 11, 12, 13, 14, 15, 16, 17, 18, 21, 22, 23, 24, 25 };// egitim
																														// verilerim

		int[] diziTest = new int[] { 0, 10, 12, 19, 20 };// //test verilerim

		EgitimeBasla(diziEgitim);
		TesteBasla(diziTest);
	}

	boolean GirisNoronEkle(double[]... gelen) throws IOException {
		int uzunluk = gelen.length;
		System.out.println(gelen[0].length);
		File file = new File("degerler123.txt");
		String line;
		FileReader fileReader;
		fileReader = new FileReader(file);
		BufferedReader br = new BufferedReader(fileReader);
		String[] eklenecek = new String[gelen[0].length];
		for (int t = 0; t < uzunluk; t++) {
			double min = minBul(gelen[t]);
			double max = maxBul(gelen[t]);
			for (int i = 0; i < gelen[t].length; i++) {
				gelen[t][i] = 0.8 * (gelen[t][i] - min) / (max - min);
				eklenecek[i] = "";
			}

		}
		for (int i = 0; i < gelen[0].length; i++) {
			for (int j = 0; j < gelen.length; j++) {
				eklenecek[i] += gelen[j][i] + " ";

			}

		}

		try {
			int i = 0;
			beklenen = new String[gelen[0].length];
			while ((line = br.readLine()) != null) {

				String[] sozcuk = line.split(" ");
				String ogut = "";
				for (int k = 0; k < sozcuk.length; k++) {
					if (k == sozcuk.length - 1)
						beklenen[i] = sozcuk[k];
					else
						ogut += sozcuk[k] + " ";

				}
				ogut = ogut + eklenecek[i] + beklenen[i] + "\n";
				bw_olustur.write(ogut);
				line = "";
				i++;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		bw_olustur.close();
		br.close();
		return true;
	}

	String[] beklenen;

	void agirlikDoldur() {
		int noronsayisi = girdiler[0].length;
		agirliklarGirdi = new double[noronsayisi][gizliKatmanNoronSayisi];
		double x = -1.0 + (Math.random() * 2);
		for (int i = 0; i < noronsayisi; i++) {

			for (int j = 0; j < gizliKatmanNoronSayisi; j++) {
				agirliklarGirdi[i][j] = -1.0 + (Math.random() * 2);
			}

		}
		agirliklarCikis = new double[gizliKatmanNoronSayisi];
		for (int i = 0; i < gizliKatmanNoronSayisi; i++) {
			agirliklarCikis[i] = -1.0 + (Math.random() * 2);

		}
	}

	static double minBul(double[] dizi) {
		double min = dizi[0];
		for (int i = 0; i < dizi.length; i++) {
			if (dizi[i] < min)
				min = dizi[i];
		}
		return min;
	}

	static double maxBul(double[] dizi) {
		double max = dizi[0];
		for (int i = 0; i < dizi.length; i++) {

			if (dizi[i] > max)
				max = dizi[i];

		}
		return max;

	}

	void noronlariDoldur() {
		int artim = 0;
		for (int i = 0; i < satirlar.length; i++) {
			String[] line = satirlar[i].split(" ");
			for (int j = 0; j < line.length; j++) {
				if (j == line.length - 1) {

					hedef[artim] = Double.parseDouble(line[j]);// CİKİS NÖRONU
					artim++;
				} else
					girdiler[i][j] = Double.parseDouble(line[j]);
			}

		}
	}

	int getirSutunSayisi() {
		String[] sutunlar = satirlar[0].split(" ");

		return sutunlar.length;
	}

	public BufferedWriter dosyaOlustur(String dosya) {

		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			File file = new File(dosya);
			file.delete();
			if (!file.exists()) {
				file.createNewFile();

				Set perms = new HashSet();// asagidaki kodlar, linuxda dosya
											// izinlerini almak için gerekli,
											// aksi durumda dosya olusturma
				// yapılamiyor
				perms.add(PosixFilePermission.OWNER_READ);
				perms.add(PosixFilePermission.OWNER_WRITE);
				perms.add(PosixFilePermission.OWNER_EXECUTE);
				perms.add(PosixFilePermission.GROUP_READ);
				perms.add(PosixFilePermission.GROUP_EXECUTE);
				perms.add(PosixFilePermission.GROUP_WRITE);
				perms.add(PosixFilePermission.OTHERS_EXECUTE);
				perms.add(PosixFilePermission.OTHERS_READ);
				perms.add(PosixFilePermission.OTHERS_WRITE);

				Files.setPosixFilePermissions(file.toPath(), perms);
				fw = new FileWriter(file.getAbsoluteFile());
				bw = new BufferedWriter(fw);
			}
		} catch (IOException ee) {
			ee.printStackTrace();
		}
		return bw;
	}

	// DOSYADAKI SATIR SAYISINI GETIRIR-yani VERİ SETİ SAYISI
	int getirSatirSayisi() throws IOException {
		File file = new File(modifiyelidegerler);
		String line;
		int i = 0;
		FileReader fileReader;
		try {
			fileReader = new FileReader(file);
			BufferedReader br = new BufferedReader(fileReader);

			while ((line = br.readLine()) != null) {

				i = i + 1;

			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return i;
	}

	// /////////////////////////////NORMALIZASYON YAPIYORUZ

	// DOSYADAN VERILERI CEKIYORUZ
	public void olusturGirdiler() throws IOException {

		File file = new File(modifiyelidegerler);
		String line;
		FileReader fileReader;
		try {
			fileReader = new FileReader(file);
			BufferedReader br = new BufferedReader(fileReader);
			int i = 0;
			while ((line = br.readLine()) != null) {

				satirlar[i] = line;
				i = i + 1;

			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void EgitimeBasla(int[] dizi) {

		BufferedWriter yaz = dosyaOlustur("hatalar.txt");

		agirlikGirdiFark = new double[girdiler[0].length][gizliKatmanNoronSayisi];

		double[] aktivasyon = new double[gizliKatmanNoronSayisi];
		agirlikCikisFark = new double[gizliKatmanNoronSayisi];
		double hatason1 = 0;

		for (int a2 = 0; a2 < 5000; a2++) {
			for (int i : dizi) {

				double aktivasyonCikis = 0;
				double[] toplam = new double[gizliKatmanNoronSayisi];
				double toplamCikis = 0;
				double hataCikis = 0;
				double[] hataAra = new double[gizliKatmanNoronSayisi];

				for (int k = 0; k < gizliKatmanNoronSayisi; k++) {
					toplam[k] = 0;
					for (int j = 0; j < girdiler[0].length; j++) {

						toplam[k] += girdiler[i][j] * agirliklarGirdi[j][k];
					}
					aktivasyon[k] = 1 / (1 + (Math.exp(-1 * toplam[k])));

					toplamCikis += aktivasyon[k] * agirliklarCikis[k];
				}

				aktivasyonCikis = 1 / (1 + (Math.exp(-1 * toplamCikis)));

				hatason1 += Math.pow(hedef[i] - aktivasyonCikis, 2);

				hataCikis = aktivasyonCikis * (1 - aktivasyonCikis) * (hedef[i] - aktivasyonCikis);
				// hatason1 += hataCikis;
				for (int k = 0; k < gizliKatmanNoronSayisi; k++) {
					hataAra[k] = aktivasyon[k] * (1 - aktivasyon[k]) * hataCikis * agirliklarCikis[k];
				}
				for (int k = 0; k < gizliKatmanNoronSayisi; k++) {
					for (int j = 0; j < girdiler[0].length; j++) {
						agirlikGirdiFark[j][k] = (agirlikGirdiFark[j][k] * momentum)
								+ (n * hataAra[k] * girdiler[i][j]);
						agirliklarGirdi[j][k] += agirlikGirdiFark[j][k];

					}
					agirlikCikisFark[k] = agirlikCikisFark[k] * momentum + (n * hataCikis * aktivasyon[k]);
					agirliklarCikis[k] += agirlikCikisFark[k];

				}

				// System.out.println(a2 + " . iter " + i + ": beklenen:" +
				// hedef[i] + " gelen : " + aktivasyonCikis
				// + " topma: ");

			}

			try {
				yaz.write(Math.abs(hatason1) + "\n");
				hatason1 = 0;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		try {
			yaz.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void TesteBasla(int[] diziTest) {
		// TODO Auto-generated method stub

		for (int i : diziTest) {

			double aktivasyonCikis = 0;
			double[] toplam = new double[gizliKatmanNoronSayisi];
			double[] aktivasyon = new double[gizliKatmanNoronSayisi];
			double toplamCikis = 0;

			for (int k = 0; k < gizliKatmanNoronSayisi; k++) {
				toplam[k] = 0;
				for (int j = 0; j < girdiler[0].length; j++) {

					toplam[k] += girdiler[i][j] * agirliklarGirdi[j][k];

				}
				aktivasyon[k] = 1 / (1 + (Math.exp(-1 * toplam[k])));

				toplamCikis += aktivasyon[k] * agirliklarCikis[k];
				// System.out.println(k);

			}
			aktivasyonCikis = 1 / (1 + (Math.exp(-1 * toplamCikis)));

			System.out.println(i + ": beklenen:" + hedef[i] + " gelen : " + aktivasyonCikis + " topma: ");

		}

	}

	public static void main(String[] args) throws IOException {

		new Doktor();
		// girdiler[][];

	}

}
