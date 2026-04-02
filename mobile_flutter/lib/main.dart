import 'package:flutter/material.dart';
import 'models/etudiant.dart';
import 'services/etudiant_services.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Liste des Étudiants',
      debugShowCheckedModeBanner: false,
      theme: ThemeData(primarySwatch: Colors.blue),
      home: const EtudiantsScreen(),
    );
  }
}

class EtudiantsScreen extends StatefulWidget {
  const EtudiantsScreen({super.key});

  @override
  State<EtudiantsScreen> createState() => _EtudiantsScreenState();
}

class _EtudiantsScreenState extends State<EtudiantsScreen> {
  final EtudiantService _service = EtudiantService();
  late Future<List<Etudiant>> _futureEtudiants;

  @override
  void initState() {
    super.initState();
    _futureEtudiants = _service.getAllEtudiants();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Liste des Étudiants'),
        centerTitle: true,
      ),
      body: FutureBuilder<List<Etudiant>>(
        future: _futureEtudiants,
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            return const Center(child: CircularProgressIndicator());
          }

          if (snapshot.hasError) {
            return Center(child: Text('Erreur : ${snapshot.error}'));
          }

          if (!snapshot.hasData || snapshot.data!.isEmpty) {
            return const Center(child: Text('Aucun étudiant trouvé'));
          }

          final etudiants = snapshot.data!;

          return ListView.builder(
            itemCount: etudiants.length,
            itemBuilder: (context, index) {
              final etudiant = etudiants[index];
              return Card(
                margin: const EdgeInsets.symmetric(horizontal: 12, vertical: 6),
                child: ListTile(
                  leading: CircleAvatar(
                    child: Text(etudiant.cin.substring(0, 2)),
                  ),
                  title: Text(etudiant.nom),
                  subtitle: Text('CIN : ${etudiant.cin}'),
                  trailing: Text(
                    etudiant.dateNaissance,
                    style: const TextStyle(fontSize: 13),
                  ),
                ),
              );
            },
          );
        },
      ),
    );
  }
}