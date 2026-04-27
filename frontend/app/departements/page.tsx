"use client";

import { useEffect, useState } from "react";
import { Departement } from "@/types";
import DepartementForm from "@/components/DepartementForm";

export default function DepartementsPage() {
  const [departements, setDepartements] = useState<Departement[]>([]);
  const [editingDept, setEditingDept] = useState<Departement | undefined>(undefined);
  const [showForm, setShowForm] = useState(false);
  const [loading, setLoading] = useState(true);

  const fetchDepartements = async () => {
    setLoading(true);
    try {
      const res = await fetch("http://localhost:8080/api/departements");
      if (res.ok) {
        const data = await res.json();
        setDepartements(data);
      }
    } catch (error) {
      console.error("Failed to fetch departments:", error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchDepartements();
  }, []);

  const handleDelete = async (id: number) => {
    if (!confirm("Voulez-vous vraiment supprimer ce département ? Cela peut affecter les étudiants rattachés.")) return;

    try {
      const res = await fetch(`http://localhost:8080/api/departements/${id}`, {
        method: "DELETE",
      });
      if (res.ok) {
        setDepartements(departements.filter(d => d.id !== id));
      } else {
        alert("Impossible de supprimer le département (vérifiez s'il y a des étudiants rattachés)");
      }
    } catch (error) {
      alert("Erreur lors de la suppression");
    }
  };

  return (
    <div className="space-y-8">
      <div className="flex justify-between items-center">
        <div>
          <h1 className="text-3xl font-bold text-slate-900">Départements</h1>
          <p className="text-slate-500 mt-1">Gérez la structure académique de l'université.</p>
        </div>
        {!showForm && (
          <button 
            onClick={() => { setEditingDept(undefined); setShowForm(true); }}
            className="px-6 py-3 bg-blue-600 text-white font-semibold rounded-xl hover:bg-blue-700 transition-colors shadow-lg shadow-blue-200"
          >
            + Nouveau Département
          </button>
        )}
      </div>

      {showForm && (
        <div className="max-w-xl mx-auto">
          <DepartementForm 
            initialData={editingDept}
            onSuccess={() => { setShowForm(false); fetchDepartements(); }}
            onCancel={() => setShowForm(false)}
          />
        </div>
      )}

      <div className="bg-white rounded-2xl shadow-sm border border-slate-200 overflow-hidden">
        <table className="w-full text-left">
          <thead className="bg-slate-50 border-b border-slate-200">
            <tr>
              <th className="px-6 py-4 text-sm font-bold text-slate-700">ID</th>
              <th className="px-6 py-4 text-sm font-bold text-slate-700">Nom du Département</th>
              <th className="px-6 py-4 text-sm font-bold text-slate-700 text-right">Actions</th>
            </tr>
          </thead>
          <tbody className="divide-y divide-slate-100">
            {loading ? (
              [1, 2, 3].map(i => (
                <tr key={i} className="animate-pulse">
                  <td colSpan={3} className="px-6 py-4 h-16 bg-slate-50/50"></td>
                </tr>
              ))
            ) : departements.length > 0 ? (
              departements.map(dept => (
                <tr key={dept.id} className="hover:bg-slate-50 transition-colors">
                  <td className="px-6 py-4 text-sm text-slate-500 font-mono">#{dept.id}</td>
                  <td className="px-6 py-4 text-lg font-medium text-slate-800">{dept.nom}</td>
                  <td className="px-6 py-4 text-right space-x-3">
                    <button 
                      onClick={() => { setEditingDept(dept); setShowForm(true); }}
                      className="text-blue-600 font-medium hover:underline"
                    >
                      Modifier
                    </button>
                    <button 
                      onClick={() => handleDelete(dept.id)}
                      className="text-red-600 font-medium hover:underline"
                    >
                      Supprimer
                    </button>
                  </td>
                </tr>
              ))
            ) : (
              <tr>
                <td colSpan={3} className="px-6 py-10 text-center text-slate-400">
                  Aucun département trouvé.
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </div>
    </div>
  );
}
