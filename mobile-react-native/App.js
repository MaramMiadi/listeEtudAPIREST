import React, { useEffect, useState } from 'react';
import { View, Text, FlatList, StyleSheet, ActivityIndicator, Alert } from 'react-native';

const GATEWAY_URL = 'http://localhost:8080/api'; // Emulateur Android (Gateway)

export default function App() {
  const [etudiants, setEtudiants] = useState([]);
  const [departements, setDepartements] = useState([]);
  const [selectedDept, setSelectedDept] = useState(null);
  const [loading, setLoading] = useState(true);

  // Charger les départements au démarrage
  useEffect(() => {
    fetch(`${GATEWAY_URL}/departements`)
      .then(res => res.json())
      .then(data => {
        setDepartements(data);
        if (data.length > 0) {
          setSelectedDept(data[0].id);
        } else {
          setLoading(false);
        }
      })
      .catch(err => {
        console.error(err);
        Alert.alert('Erreur', 'Impossible de charger les départements');
        setLoading(false);
      });
  }, []);

  // Charger les étudiants quand le département change
  useEffect(() => {
    if (selectedDept) {
      setLoading(true);
      fetch(`${GATEWAY_URL}/etudiants?departementId=${selectedDept}`)
        .then(res => res.json())
        .then(data => {
          setEtudiants(data);
          setLoading(false);
        })
        .catch(err => {
          console.error(err);
          Alert.alert('Erreur', 'Impossible de charger les étudiants');
          setLoading(false);
        });
    }
  }, [selectedDept]);

  const renderEtudiant = ({ item }) => (
    <View style={styles.card}>
      <Text style={styles.nom}>{item.nom}</Text>
      <Text style={styles.cin}>CIN : {item.cin}</Text>
      <Text style={styles.date}>Né(e) le : {item.dateNaissance}</Text>
    </View>
  );

  const renderDeptButton = ({ item }) => (
    <Text
      style={[styles.deptButton, selectedDept === item.id && styles.activeDept]}
      onPress={() => setSelectedDept(item.id)}
    >
      {item.nom}
    </Text>
  );

  return (
    <View style={styles.container}>
      <Text style={styles.title}>Gestion des Étudiants</Text>

      <View style={styles.deptContainer}>
        <Text style={styles.label}>Filtrer par Département :</Text>
        <FlatList
          horizontal
          showsHorizontalScrollIndicator={false}
          data={departements}
          keyExtractor={item => item.id.toString()}
          renderItem={renderDeptButton}
          contentContainerStyle={styles.deptList}
        />
      </View>

      {loading ? (
        <View style={styles.center}>
          <ActivityIndicator size="large" color="#007AFF" />
        </View>
      ) : (
        <FlatList
          data={etudiants}
          keyExtractor={item => item.id.toString()}
          renderItem={renderEtudiant}
          contentContainerStyle={styles.list}
          ListEmptyComponent={<Text style={styles.empty}>Aucun étudiant dans ce département</Text>}
        />
      )}
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#f5f5f5',
    paddingTop: 50,
  },
  title: {
    fontSize: 24,
    fontWeight: 'bold',
    textAlign: 'center',
    marginBottom: 10,
    color: '#007AFF',
  },
  label: {
    fontSize: 16,
    fontWeight: 'bold',
    marginHorizontal: 15,
    marginBottom: 10,
  },
  deptContainer: {
    marginBottom: 10,
  },
  deptList: {
    paddingHorizontal: 10,
    paddingBottom: 5,
  },
  deptButton: {
    backgroundColor: '#eee',
    paddingHorizontal: 15,
    paddingVertical: 8,
    borderRadius: 20,
    marginHorizontal: 5,
    fontSize: 14,
    color: '#333',
    overflow: 'hidden',
  },
  activeDept: {
    backgroundColor: '#007AFF',
    color: 'white',
  },
  list: {
    paddingHorizontal: 15,
    paddingBottom: 20,
  },
  card: {
    backgroundColor: 'white',
    padding: 15,
    marginVertical: 8,
    borderRadius: 10,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.1,
    shadowRadius: 4,
    elevation: 3,
  },
  nom: {
    fontSize: 18,
    fontWeight: '600',
  },
  cin: {
    fontSize: 16,
    color: '#555',
    marginTop: 4,
  },
  date: {
    fontSize: 15,
    color: '#777',
    marginTop: 4,
  },
  empty: {
    textAlign: 'center',
    marginTop: 50,
    color: '#999',
    fontSize: 16,
  },
  center: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
});