import 'dart:convert';
import 'package:http/http.dart' as http;
import '../models/etudiant.dart';

class EtudiantService {
  // Utilise 10.0.2.2 pour l'émulateur Android pour accéder au localhost du PC
  static const String baseUrl = 'http://localhost:8080/api';

  Future<List<Etudiant>> getEtudiantsByDepartement(int? deptId) async {
    String url = '$baseUrl/etudiants';
    if (deptId != null) {
      url += '?departementId=$deptId';
    }
    final response = await http.get(Uri.parse(url));

    if (response.statusCode == 200) {
      List<dynamic> data = json.decode(response.body);
      return data.map((json) => Etudiant.fromJson(json)).toList();
    } else {
      throw Exception('Erreur lors du chargement des étudiants');
    }
  }

  Future<List<dynamic>> getDepartements() async {
    final response = await http.get(Uri.parse('$baseUrl/departements'));
    if (response.statusCode == 200) {
      return json.decode(response.body);
    } else {
      throw Exception('Erreur lors du chargement des départements');
    }
  }
}