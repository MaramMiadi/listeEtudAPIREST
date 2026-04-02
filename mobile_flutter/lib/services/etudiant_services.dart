import 'dart:convert';
import 'package:http/http.dart' as http;
import '../models/etudiant.dart';

class EtudiantService {
  // Utilise ton IP machine (ex: 192.168.1.XX) ou 10.0.2.2 pour l'émulateur Android
  static const String baseUrl = 'http://10.0.2.2:8080/api/etudiants';

  Future<List<Etudiant>> getAllEtudiants() async {
    final response = await http.get(Uri.parse(baseUrl));

    if (response.statusCode == 200) {
      List<dynamic> data = json.decode(response.body);
      return data.map((json) => Etudiant.fromJson(json)).toList();
    } else {
      throw Exception('Erreur lors du chargement des étudiants');
    }
  }
}