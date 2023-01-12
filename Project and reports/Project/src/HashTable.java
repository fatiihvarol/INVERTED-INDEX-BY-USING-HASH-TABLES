import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;

public class HashTable {

	private HashEntry search;
	private HashEntry[] table;
	private SingleLinkedList SLL;
	private int dk = 0;
	@SuppressWarnings("unused")
	private int collision = 0;
	@SuppressWarnings("unused")

	private int Table_Size = 128;
	// private int load_factor = (Table_Size / 2);
	private int load_factor = Table_Size - (Table_Size / 5);
	private int control_resize = 0;

	public int getCollision() {
		return collision;
	}

	public HashTable() {

		table = new HashEntry[Table_Size];
		for (int i = 0; i < Table_Size; i++) {
			table[i] = null;
		}
	}

	public void forSLL(String[] words, String file) {// SingleLinkedListe kelimenin hangi dosyalarda kaç tane olduðunu
														// bulup ekler.
		int number_of_word = 0;
		try {
			for (int i = 0; i < Table_Size; i++) {
				if (table[i] != null) {
					number_of_word = 0;
					SLL = new SingleLinkedList(table[i].getValue());
					table[i].setSLL(SLL);
					for (int j = 0; j < words.length; j++) {
						if (words[j] != null && table[i].getValue().getData().toString().equals(words[j])) {
							number_of_word++;
						}
					}

					SLL.add(file + " " + number_of_word);
				}
			}

		} catch (Exception e) {

		}

	}

	public int SSF(String word) {
		int key = 0;
		for (int i = 0; i < word.length(); i++) {
			key += word.charAt(i);
		}
		return key;
	}

	public long PAF(String words) {
		long key = 0;
		for (int i = 0; i < words.length(); i++) {
			key += (words.charAt(i) - 96) * Math.pow(41, i);

		}
		return key;
	}

	public int hashFunction(long key) {

		int hash_code = (int) (key % Table_Size);
		return hash_code;
	}

	public int LP(int hash_code) {// Linear Probing

		while (table[hash_code] != null) {
			collision++;

			if (hash_code > (Table_Size - 1)) {

				// hash_code++;
				hash_code = hash_code % Table_Size;

			} else {
				hash_code++;
			}

		}
		return hash_code;
	}

	public int DH(long key) {// Double Hashing
		// int prime_number;
		int first_hash_code = hashFunction(key);
		int hash_code = first_hash_code;
		if (table[hash_code] != null) {
			dk = (int) (127 - (key % 127));
			int j = 1;
			while (table[hash_code] != null) {
				collision++;
				hash_code = (first_hash_code + (j * dk)) % Table_Size;
				j++;

			}

		}
		return hash_code;
	}

	public void Resize() {

		boolean flag = false;
		HashEntry[] resize = table;

		this.Table_Size = Table_Size * 2;
		while (!flag) {
			flag = true;
			for (int i = 2; i < Table_Size; i++) {
				if (Table_Size % i == 0) {
					flag = false;
					this.Table_Size++;
					break;
				}

			}

		}
		load_factor = (Table_Size / 2);

		table = new HashEntry[this.Table_Size];
		for (int i = 0; i < resize.length; i++) {
			try {
				put(resize[i].getKey(), resize[i].getValue());

			} catch (Exception e) {
				// TODO: handle exception
			}
		}

	}

	public void put(long key, Node value) {

		int hash_code = hashFunction(key);
		try {
			if (table[hash_code] == null) {
				table[hash_code] = new HashEntry(key, value);
			} else {
				// table[LP(hash_code)] = new HashEntry(key, value);
				table[DH(key)] = new HashEntry(key, value);

			}

			control_resize++;

			if (control_resize >= load_factor) {
				Resize();
				control_resize = 0;

			}

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public void printhash() {// Tüm Hash Table'ý yazdýrma
		for (int i = 0; i < Table_Size; i++) {
			try {
				System.out.print("\n" + table[i].getValue().getData() + " ---> ");
				table[i].getSLL().print();

			} catch (Exception e) {

			}

		}

	}

	public boolean isContainsLP(String word) {// Linear Probing için aranan kelimenin hash table içinde olup olmadýðýný
												// söyler.
		// int key = SSF(word);
		long key = PAF(word);
		int hash_code = hashFunction(key);
		boolean flag = false;

		if (table[hash_code] == null) {
			flag = false;
		} else {

			while (table[hash_code] != null) {

				if (table[hash_code].getValue().getData().equals(word)) {
					flag = true;
					search = table[hash_code];
				}
				hash_code++;

				if (hash_code > (Table_Size - 1)) {

					hash_code = hash_code % Table_Size;

				}

			}

		}

		return flag;
	}

	public boolean isContainsDH(String word) {// Double Hashing için aranan kelimenin hash table içinde olup olmadýðýný
												// söyler.
		// int key = SSF(word);
		long key = PAF(word);
		dk = (int) (127 - (key % 127));
		int hash_code = hashFunction(key);
		boolean flag = false;

		if (table[hash_code] == null) {
			flag = false;
		} else {

			while (table[hash_code] != null) {

				if (table[hash_code].getValue().getData().equals(word)) {
					flag = true;
					search = table[hash_code];
				}

				hash_code += dk;
				hash_code = hash_code % Table_Size;

			}
		}

		return flag;

	}

	public void Search() {// Tek input ile elle arama.
		System.out.print("Please enter a word for search: ");
		Scanner scan = new Scanner(System.in);
		String word = scan.next();
		word = word.toLowerCase(Locale.ENGLISH).trim();

//		if (isContainsDH(word) {
//
//			System.out.print("\n"+search.getValue().getData() + " ---> ");
//			search.getSLL().print();
//
//		}
		if (isContainsLP(word)) {

			System.out.print("\n" + search.getValue().getData() + " ---> ");
			search.getSLL().print();

		} else {
			System.out.println("\n" + String.valueOf(search.getValue().getData()).toUpperCase() + " Not Found!\n");
		}

	}

	public void Search1000() throws IOException {// 1000.txt için arama
		double min_search = Integer.MAX_VALUE;
		double max_search = 0;
		double sumTime = 0;
		int notfound = 0;
		System.out.println("----------------SEARCH----------------");
		String words1000 = "";
		@SuppressWarnings("unused")
		String txt = "";

		@SuppressWarnings("resource")
		BufferedReader br = new BufferedReader(new FileReader("1000.txt"));
		while ((words1000 = br.readLine()) != null) {
			txt += words1000 + " ";
		}
		txt = txt.replaceAll(System.getProperty("line.separator"), "");
		@SuppressWarnings("null")
		String[] words = txt.split(" ");
		for (int i = 0; i < words.length; i++) {
			long startTime = System.nanoTime();
			if (isContainsDH(words[i])) {

				System.out.print("\n" + search.getValue().getData() + " ---> ");
				search.getSLL().print();

			}

//			if (isContainsLP(words[i])) {
//
//				//System.out.print("\n" + search.getValue().getData() + " ---> ");
//				//search.getSLL().print();
//
//			} 

			else {
				notfound++;
				System.out.println("\n" + String.valueOf(search.getValue().getData()).toUpperCase() + " Not Found!\n");
			}
			long endTime = System.nanoTime();
			long totalTime = endTime - startTime;
			sumTime += totalTime;
			if (totalTime > max_search) {
				max_search = totalTime;
			}
			if (totalTime < min_search) {
				min_search = totalTime;
			}

		}
//		sumTime=sumTime/1000;
//		System.out.println("Min Time: "+min_search);
//		System.out.println("Max Time: "+max_search);
//		System.out.println("Average Time: "+sumTime);
		System.out.println("\n" + notfound + " words not found !");
	}
}
