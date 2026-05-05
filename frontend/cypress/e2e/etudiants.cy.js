describe('Gestion des étudiants', () => {
    it('affiche la liste des étudiants', () => {
        cy.visit('http://localhost:3000/etudiants');
        
        // Wait for loading to finish (both students and departments fetches)
        // The loading skeleton has the 'animate-pulse' class.
        cy.get('.animate-pulse', { timeout: 10000 }).should('not.exist');

        cy.contains('Liste des Étudiants').should('be.visible');
        
        // Now check the content
        cy.get('body').then(($body) => {
            if ($body.find('h3').length > 0) {
                cy.get('h3').should('have.length.greaterThan', 0);
            } else {
                cy.contains('Aucun étudiant trouvé.').should('be.visible');
            }
        });
    });

    it('crée un nouvel étudiant', () => {
        const uniqueId = Math.floor(10000000 + Math.random() * 90000000).toString();
        cy.visit('http://localhost:3000/etudiants/new');
        
        cy.contains('label', 'Nom Complet').parent().find('input').type('Alice Martin');
        cy.contains('label', 'CIN').parent().find('input').type(uniqueId);
        cy.contains('label', 'Email').parent().find('input').type(`alice.${uniqueId}@example.com`);
        cy.contains('label', 'Date de Naissance').parent().find('input').type('2000-01-01');
        cy.contains('label', "Année d'Inscription").parent().find('input').clear().type('2023');
        cy.contains('label', 'Nom Complet').parent().find('input').type('Alice Martin');

        cy.get('[type="submit"]').click();
        
        // Ensure redirection happens and the new student is displayed in the list
        cy.contains('Liste des Étudiants', { timeout: 10000 }).should('be.visible');
        cy.contains('Alice Martin').should('be.visible');
    });
});