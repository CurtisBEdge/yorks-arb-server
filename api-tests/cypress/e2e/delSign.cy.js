describe('delete sign', () => {
  beforeEach(() => {
    cy.deleteUsers();
    cy.createTestAdmin();
  })
  it('delete sign', () => {
    cy.login()
      .then((response) => {
        const token = response.body.token;
        cy.createSignwithoutImg(token).then(({ body }) => {
          const bodyString = Cypress.Blob.arrayBufferToBinaryString(body);
          const bodyJson = JSON.parse(bodyString);
          const signId = bodyJson.id
          cy.request({
            url: `/signs/${signId}`,
            method: "DELETE",
            headers: {
              Authorization: `bearer ${token}`,
            },
          }).then(({ status }) => {
            expect(status).to.eq(200)
          })
          cy.request({
            url: "/signs/get",
            method: "GET",
            headers: {
              Authorization: `bearer ${token}`,
            },
          }).then(({ body }) => {
            expect(!body.some((sign) => sign.id === signId)).to.eq(true)
          })
        })
      })
  })
})
