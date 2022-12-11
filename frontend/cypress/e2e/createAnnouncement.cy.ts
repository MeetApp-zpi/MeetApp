/* eslint-disable @typescript-eslint/no-unused-vars */
/// <reference types="cypress" />

describe('Create announcement', () => {
    beforeEach(() => {
        cy.loginByGoogleApi();
    });

    it('Create a new announcement', () => {
        cy.visit('announcements');

        cy.get('[data-cy="createAnn"]').click();
        cy.get('[data-cy="titleInput"]').type('Testowa nazwa');
        cy.get('.categorySvelecteBox').click();
        cy.contains('Sport').click();
        cy.get('.categorySvelecteBox').click();
        cy.get('[id=cityInputBox]').click().type('Wr');
        cy.contains('Wrocław').click();
        cy.get('textarea').type('Moje nowe super ogłoszenie');
        cy.get('[data-cy="submitAnn"]').click();

        cy.contains('Testowa nazwa');
    });
});