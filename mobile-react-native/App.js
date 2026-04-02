import React, { useEffect, useState } from 'react';
import { View, Text, FlatList, StyleSheet, ActivityIndicator, Alert } from 'react-native';

const API_URL = 'http://10.0.2.2:8080/api/etudiants';   // émulateur Android
// Si tu testes sur vrai téléphone → remplace par ton IP Windows (ex: http://192.168.1.XX:8080/api/etudiants)

export default function App() {
  const [etudiants, setEtudiants] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetch(API_URL)
      .then(response => {
        if (!response.ok) throw new Error('Erreur réseau');
        return response.json();
      })
      .then(data => {
        setEtudiants(data);
        setLoading(false);
      })
      .catch(error => {
        console.error(error);
        Alert.alert('Erreur', 'Impossible de charger les étudiants');
        setLoading(false);
      });
  }, []);

  const renderItem = ({ item }) => (
    <View style={styles.card}>
      <Text style={styles.nom}>{item.nom}</Text>
      <Text style={styles.cin}>CIN : {item.cin}</Text>
      <Text style={styles.date}>Date de naissance : {item.dateNaissance}</Text>
    </View>
  );

  if (loading) {
    return (
      <View style={styles.center}>
        <ActivityIndicator size="large" color="#007AFF" />
      </View>
    );
  }

  return (
    <View style={styles.container}>
      <Text style={styles.title}>Liste des Étudiants</Text>
      <FlatList
        data={etudiants}
        keyExtractor={item => item.id.toString()}
        renderItem={renderItem}
        contentContainerStyle={styles.list}
      />
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
    marginBottom: 20,
    color: '#007AFF',
  },
  list: {
    paddingHorizontal: 15,
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
  center: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
});