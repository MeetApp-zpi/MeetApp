/// <reference types="cypress" />

describe('Browse authorized', () => {

    beforeEach(() => {
        cy.loginByGoogleApi();
    });

    it('Visit home page', () => {
        cy.visit('/');
    });

    it('Visit announcements page', () => {
        cy.visit('/announcements');
    });

    it('Visit meetings page', () => {
        cy.visit('/meetings');
    });

    it('Visit events page', () => {
        cy.visit('/events');
    });

    it('Visit events inactive page', () => {
        cy.visit('/eventsInactive');
    });

    it('Visit terms of use page', () => {
        cy.visit('/termsOfUse');
    });

    it('Visit my profile page', () => {
        cy.visit('/profile');

        cy.contains('Jan Testowicz');
    });

    it('Visit my posts page', () => {
        cy.visit('/user/posts');

        cy.contains('Testowe wydarzenie');
    });

    it('Visit my inactive posts page', () => {
        cy.visit('/user/postsInactive');
    });

    it('Visit myActivities', () => {
        cy.visit('/myActivities');
    });

    it('Visit choose categories page', () => {
        cy.visit('/chooseCategories');
    });

    it('Visit chatrooms page', () => {
        cy.visit('/chatrooms');
    });

    it('Visit enrolled page', () => {
        cy.visit('/enrolled/1');
    });

    it('Visit others profile page', () => {
        cy.visit('/profile/2');

        cy.contains('Rolkowy Świrus');
    });

    it('Visit others posts page', () => {
        cy.visit('/user/2/posts');

        cy.contains('Mocna ekipa szuka rolkarza');
    });

    // No test chats in DB
    // it('Visit chat page', () => {
    //     cy.visit('/chat/1');
    // });
});