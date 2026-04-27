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
  List<dynamic> _departements = [];
  int? _selectedDeptId;
  late Future<List<Etudiant>> _futureEtudiants;

  @override
  void initState() {
    super.initState();
    _loadDepartements();
    _futureEtudiants = _service.getEtudiantsByDepartement(null);
  }

  void _loadDepartements() async {
    try {
      final depts = await _service.getDepartements();
      setState(() {
        _departements = depts;
        if (depts.isNotEmpty) {
          _selectedDeptId = depts[0]['id'];
          _futureEtudiants = _service.getEtudiantsByDepartement(_selectedDeptId);
        }
      });
    } catch (e) {
      debugPrint('Error loading depts: $e');
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Gestion des Étudiants'),
        centerTitle: true,
      ),
      body: Column(
        children: [
          Padding(
            padding: const EdgeInsets.all(16.0),
            child: Row(
              children: [
                const Text('Département : ', style: TextStyle(fontWeight: FontWeight.bold)),
                Expanded(
                  child: DropdownButton<int>(
                    isExpanded: true,
                    value: _selectedDeptId,
                    items: _departements.map((dept) {
                      return DropdownMenuItem<int>(
                        value: dept['id'],
                        child: Text(dept['nom']),
                      );
                    }).toList(),
                    onChanged: (value) {
                      setState(() {
                        _selectedDeptId = value;
                        _futureEtudiants = _service.getEtudiantsByDepartement(value);
                      });
                    },
                  ),
                ),
              ],
            ),
          ),
          Expanded(
            child: FutureBuilder<List<Etudiant>>(
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
                          child: Text(etudiant.nom.substring(0, 1).toUpperCase()),
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
          ),
        ],
      ),
    );
  }
}