"use client";

import { useEffect, useState } from "react";
import { useRouter, useParams } from "next/navigation";
import { Departement, Etudiant } from "@/types";
import Link from "next/link";

export default function EtudiantFormPage() {
  const router = useRouter();
  const params = useParams();
  const id = params.id as string;
  const isNew = id === "new";

  const [departements, setDepartements] = useState<Departement[]>([]);
  const [formData, setFormData] = useState({
    nom: "",
    cin: "",
    email: "",
    dateNaissance: "",
    anneePremiereInscription: new Date().getFullYear(),
    departementId: 0,
  });
  const [loading, setLoading] = useState(!isNew);
  const [saving, setSaving] = useState(false);

  useEffect(() => {
    const fetchInitialData = async () => {
      try {
        const deptRes = await fetch("http://localhost:8080/api/departements");
        const depts = await deptRes.json();
        setDepartements(depts);

        if (!isNew) {
          const etudRes = await fetch(`http://localhost:8080/api/etudiants/${id}`);
          if (etudRes.ok) {
            const etud = await etudRes.json();
            setFormData({
              nom: etud.nom,
              cin: etud.cin,
              email: etud.email,
              dateNaissance: etud.dateNaissance,
              anneePremiereInscription: etud.anneePremiereInscription,
              departementId: etud.departementId,
            });
          }
        } else if (depts.length > 0) {
          setFormData(prev => ({ ...prev, departementId: depts[0].id }));
        }
      } catch (error) {
        console.error("Error fetching data:", error);
      } finally {
        setLoading(false);
      }
    };

    fetchInitialData();
  }, [id, isNew]);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setSaving(true);

    const url = isNew 
      ? "http://localhost:8080/api/etudiants" 
      : `http://localhost:8080/api/etudiants/${id}`;
    
    const method = isNew ? "POST" : "PUT";

    try {
      const res = await fetch(url, {
        method,
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(formData),
      });

      if (res.ok) {
        router.push("/etudiants");
        router.refresh();
      } else {
        const err = await res.json();
        alert(`Erreur: ${err.message || "Échec de l'opération"}`);
      }
    } catch (error) {
      alert("Erreur de connexion au serveur");
    } finally {
      setSaving(false);
    }
  };

  if (loading) return <div className="text-center py-20">Chargement du formulaire...</div>;

  return (
    <div className="max-w-2xl mx-auto">
      <div className="mb-8">
        <Link href="/etudiants" className="text-blue-600 hover:underline mb-4 inline-block">
          &larr; Retour à la liste
        </Link>
        <h1 className="text-3xl font-bold text-slate-900">
          {isNew ? "Nouvel Étudiant" : "Modifier l'Étudiant"}
        </h1>
      </div>

      <form onSubmit={handleSubmit} className="bg-white p-8 rounded-2xl shadow-sm border border-slate-200 space-y-6">
        <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
          <div className="space-y-2">
            <label className="text-sm font-semibold text-slate-700">Nom Complet</label>
            <input
              type="text"
              required
              value={formData.nom}
              onChange={(e) => setFormData({ ...formData, nom: e.target.value })}
              className="w-full px-4 py-2 border border-slate-300 rounded-lg focus:ring-2 focus:ring-blue-500 outline-none"
            />
          </div>
          <div className="space-y-2">
            <label className="text-sm font-semibold text-slate-700">CIN</label>
            <input
              type="text"
              required
              value={formData.cin}
              onChange={(e) => setFormData({ ...formData, cin: e.target.value })}
              className="w-full px-4 py-2 border border-slate-300 rounded-lg focus:ring-2 focus:ring-blue-500 outline-none"
            />
          </div>
        </div>

        <div className="space-y-2">
          <label className="text-sm font-semibold text-slate-700">Email</label>
          <input
            type="email"
            required
            value={formData.email}
            onChange={(e) => setFormData({ ...formData, email: e.target.value })}
            className="w-full px-4 py-2 border border-slate-300 rounded-lg focus:ring-2 focus:ring-blue-500 outline-none"
          />
        </div>

        <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
          <div className="space-y-2">
            <label className="text-sm font-semibold text-slate-700">Date de Naissance</label>
            <input
              type="date"
              required
              value={formData.dateNaissance}
              onChange={(e) => setFormData({ ...formData, dateNaissance: e.target.value })}
              className="w-full px-4 py-2 border border-slate-300 rounded-lg focus:ring-2 focus:ring-blue-500 outline-none"
            />
          </div>
          <div className="space-y-2">
            <label className="text-sm font-semibold text-slate-700">Année d'Inscription</label>
            <input
              type="number"
              required
              value={formData.anneePremiereInscription}
              onChange={(e) => setFormData({ ...formData, anneePremiereInscription: Number(e.target.value) })}
              className="w-full px-4 py-2 border border-slate-300 rounded-lg focus:ring-2 focus:ring-blue-500 outline-none"
            />
          </div>
        </div>

        <div className="space-y-2">
          <label className="text-sm font-semibold text-slate-700">Département</label>
          <select
            required
            value={formData.departementId}
            onChange={(e) => setFormData({ ...formData, departementId: Number(e.target.value) })}
            className="w-full px-4 py-2 border border-slate-300 rounded-lg focus:ring-2 focus:ring-blue-500 outline-none"
          >
            {departements.map(dept => (
              <option key={dept.id} value={dept.id}>{dept.nom}</option>
            ))}
          </select>
        </div>

        <div className="pt-4">
          <button
            type="submit"
            disabled={saving}
            className="w-full bg-blue-600 text-white py-3 rounded-xl font-bold text-lg hover:bg-blue-700 disabled:opacity-50 transition-all shadow-lg shadow-blue-200"
          >
            {saving ? "Traitement..." : isNew ? "Créer l'étudiant" : "Mettre à jour"}
          </button>
        </div>
      </form>
    </div>
  );
}
