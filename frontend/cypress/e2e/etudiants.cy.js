// cypress/e2e/etudiants.cy.js
describe('Gestion des étudiants', () => {
    it('affiche la liste des étudiants', () => {
        cy.visit('http://localhost:3000/etudiants');
        cy.get('[data-testid="etudiant-list"]').should('be.visible');
        cy.get('[data-testid="etudiant-item"]').should('have.length.greaterThan', 0);
    });

    it('crée un nouvel étudiant', () => {
        cy.visit('http://localhost:3000/etudiants/nouveau');
        cy.get('[name="nom"]').type('Alice Martin');
        cy.get('[name="cin"]').type('12345678');
        cy.get('[type="submit"]').click();
        cy.contains('Alice Martin').should('be.visible');
    });
});
