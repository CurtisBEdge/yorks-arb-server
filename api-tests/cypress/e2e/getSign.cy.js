describe('getting sign', () => {
  beforeEach(() => {
    cy.deleteSigns();
  })
  it('can get a sign', () => {
    cy.createTestSign().then(({ body }) => {
      const signId = body.id;
      cy.request({
        url: `/signs/get/${signId}`,
        method: 'get',
      }).then(({ body }) => {
        expect(body.title).to.eq('Placeholder Title');
        expect(body.description).to.eq('Placeholder description');
        // expect(body.lat).to.eq(23.6);
        // expect(body.lon).to.eq(67.5);
      });
    })
  });
  it('sign not found error', () => {
    cy.request({
      url: `/signs/get/1`,
      method: 'get',
      failOnStatusCode: false,
    }).then(({ body, status }) => {
      expect(status).to.eq(404)
      expect(body.message).to.eq('Sign not found')
    })
  })
})
