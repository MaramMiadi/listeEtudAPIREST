"use client";

import { useEffect, useState } from "react";
import { Etudiant, Departement } from "@/types";
import EtudiantCard from "@/components/EtudiantCard";
import Link from "next/link";

export default function EtudiantsPage() {
  const [etudiants, setEtudiants] = useState<Etudiant[]>([]);
  const [departements, setDepartements] = useState<Departement[]>([]);
  const [selectedDept, setSelectedDept] = useState<number | "all">("all");
  const [loading, setLoading] = useState(true);

  const fetchData = async () => {
    setLoading(true);
    try {
      const [etudRes, deptRes] = await Promise.all([
        fetch(`http://localhost:8080/api/etudiants${selectedDept !== "all" ? `?departementId=${selectedDept}` : ""}`),
        fetch("http://localhost:8080/api/departements")
      ]);

      if (etudRes.ok && deptRes.ok) {
        const etudData = await etudRes.json();
        const deptData = await deptRes.json();
        setEtudiants(etudData);
        setDepartements(deptData);
      }
    } catch (error) {
      console.error("Failed to fetch data:", error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchData();
  }, [selectedDept]);

  const handleDelete = async (id: number) => {
    if (!confirm("Voulez-vous vraiment supprimer cet étudiant ?")) return;

    try {
      const res = await fetch(`http://localhost:8080/api/etudiants/${id}`, {
        method: "DELETE",
      });
      if (res.ok) {
        setEtudiants(etudiants.filter(e => e.id !== id));
      }
    } catch (error) {
      alert("Erreur lors de la suppression");
    }
  };

  return (
    <div className="space-y-8">
      <div className="flex flex-col md:flex-row md:items-center justify-between gap-4">
        <div>
          <h1 className="text-3xl font-bold text-slate-900">Liste des Étudiants</h1>
          <p className="text-slate-500 mt-1">Gérez les inscriptions et les dossiers étudiants.</p>
        </div>
        <Link 
          href="/etudiants/new" 
          className="inline-flex items-center justify-center px-6 py-3 bg-blue-600 text-white font-semibold rounded-xl hover:bg-blue-700 transition-colors shadow-lg shadow-blue-200"
        >
          + Ajouter un étudiant
        </Link>
      </div>

      <div className="bg-white p-4 rounded-xl shadow-sm border border-slate-200 flex flex-wrap items-center gap-4">
        <label className="text-sm font-semibold text-slate-700">Filtrer par département :</label>
        <select 
          value={selectedDept}
          onChange={(e) => setSelectedDept(e.target.value === "all" ? "all" : Number(e.target.value))}
          className="px-4 py-2 bg-slate-50 border border-slate-200 rounded-lg focus:ring-2 focus:ring-blue-500 outline-none transition-all"
        >
          <option value="all">Tous les départements</option>
          {departements.map(dept => (
            <option key={dept.id} value={dept.id}>{dept.nom}</option>
          ))}
        </select>
      </div>

      {loading ? (
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6 animate-pulse">
          {[1, 2, 3, 4, 5, 6].map(i => (
            <div key={i} className="h-64 bg-slate-200 rounded-xl"></div>
          ))}
        </div>
      ) : etudiants.length > 0 ? (
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {etudiants.map(etudiant => (
            <EtudiantCard 
              key={etudiant.id} 
              etudiant={etudiant} 
              onDelete={handleDelete}
            />
          ))}
        </div>
      ) : (
        <div className="text-center py-20 bg-white rounded-2xl border border-dashed border-slate-300">
          <p className="text-slate-400 text-lg">Aucun étudiant trouvé.</p>
        </div>
      )}
    </div>
  );
}
