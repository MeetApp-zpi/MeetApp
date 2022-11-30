import{S,i as j,s as A,ad as O,q as T,c as d,r as L,j as M,k as b,m as k,u as q,ae as B,t as _,d as p,l as $,e as g,a4 as C,a8 as N,n as f,T as H,Q as D,R as E,a1 as G}from"./index.54067895.js";import{H as Q}from"./Header.13336190.js";import{e as R}from"./fetchWrapper.9cb28478.js";import{A as z}from"./AnnouncementListElem.bccc46a9.js";import{E as F}from"./EventListElem.44aab28f.js";import{M as J}from"./MeetingListElem.8d26e7cd.js";import"./stores.95bcd4bd.js";import"./FaMapMarkedAlt.ca3337cf.js";import"./Button.dfe9aea7.js";import"./MdPeople.8427c1ca.js";import"./MdAccessTime.9c39c8dd.js";function w(l,n,a){const o=l.slice();return o[10]=n[a],o}function K(l){return{c:f,m:f,p:f,i:f,o:f,d:f}}function P(l){let n,a,o=l[0],e=[];for(let t=0;t<o.length;t+=1)e[t]=v(w(l,o,t));const r=t=>p(e[t],1,1,()=>{e[t]=null});return{c(){for(let t=0;t<e.length;t+=1)e[t].c();n=H()},m(t,i){for(let c=0;c<e.length;c+=1)e[c].m(t,i);b(t,n,i),a=!0},p(t,i){if(i&15){o=t[0];let c;for(c=0;c<o.length;c+=1){const h=w(t,o,c);e[c]?(e[c].p(h,i),_(e[c],1)):(e[c]=v(h),e[c].c(),_(e[c],1),e[c].m(n.parentNode,n))}for(D(),c=o.length;c<e.length;c+=1)r(c);E()}},i(t){if(!a){for(let i=0;i<o.length;i+=1)_(e[i]);a=!0}},o(t){e=e.filter(Boolean);for(let i=0;i<e.length;i+=1)p(e[i]);a=!1},d(t){G(e,t),t&&$(n)}}}function U(l){let n,a;function o(){return l[7](l[10])}return n=new z({props:{data:l[10],areDetailsShown:l[1]===l[10].id,clickHandler:o}}),{c(){d(n.$$.fragment)},m(e,r){k(n,e,r),a=!0},p(e,r){l=e;const t={};r&1&&(t.data=l[10]),r&3&&(t.areDetailsShown=l[1]===l[10].id),r&1&&(t.clickHandler=o),n.$set(t)},i(e){a||(_(n.$$.fragment,e),a=!0)},o(e){p(n.$$.fragment,e),a=!1},d(e){g(n,e)}}}function V(l){let n,a;function o(){return l[6](l[10])}return n=new F({props:{data:l[10],clickHandler:o}}),{c(){d(n.$$.fragment)},m(e,r){k(n,e,r),a=!0},p(e,r){l=e;const t={};r&1&&(t.data=l[10]),r&5&&(t.clickHandler=o),n.$set(t)},i(e){a||(_(n.$$.fragment,e),a=!0)},o(e){p(n.$$.fragment,e),a=!1},d(e){g(n,e)}}}function W(l){let n,a;function o(){return l[5](l[10])}return n=new J({props:{data:l[10],areDetailsShown:l[1]===l[10].id,clickHandler:o}}),{c(){d(n.$$.fragment)},m(e,r){k(n,e,r),a=!0},p(e,r){l=e;const t={};r&1&&(t.data=l[10]),r&3&&(t.areDetailsShown=l[1]===l[10].id),r&1&&(t.clickHandler=o),n.$set(t)},i(e){a||(_(n.$$.fragment,e),a=!0)},o(e){p(n.$$.fragment,e),a=!1},d(e){g(n,e)}}}function v(l){let n,a,o,e,r,t;const i=[W,V,U],c=[];function h(u,m){return m&1&&(n=null),m&1&&(a=null),n==null&&(n=!!Object.hasOwn(u[10],"meetingDateTime")),n?0:(a==null&&(a=!!Object.hasOwn(u[10],"startDateTime")),a?1:2)}return o=h(l,-1),e=c[o]=i[o](l),{c(){e.c(),r=H()},m(u,m){c[o].m(u,m),b(u,r,m),t=!0},p(u,m){let s=o;o=h(u,m),o===s?c[o].p(u,m):(D(),p(c[s],1,1,()=>{c[s]=null}),E(),e=c[o],e?e.p(u,m):(e=c[o]=i[o](u),e.c()),_(e,1),e.m(r.parentNode,r))},i(u){t||(_(e),t=!0)},o(u){p(e),t=!1},d(u){c[o].d(u),u&&$(r)}}}function X(l){return{c:f,m:f,p:f,i:f,o:f,d:f}}function Y(l){let n,a,o,e;a=new Q({});let r={ctx:l,current:null,token:null,hasCatch:!1,pending:X,then:P,catch:K,value:9,blocks:[,,,]};return O(l[4],r),{c(){n=T("div"),d(a.$$.fragment),o=L(),r.block.c(),M(n,"class","h-screen")},m(t,i){b(t,n,i),k(a,n,null),q(n,o),r.block.m(n,r.anchor=null),r.mount=()=>n,r.anchor=null,e=!0},p(t,[i]){l=t,B(r,l,i)},i(t){e||(_(a.$$.fragment,t),_(r.block),e=!0)},o(t){p(a.$$.fragment,t);for(let i=0;i<3;i+=1){const c=r.blocks[i];p(c)}e=!1},d(t){t&&$(n),g(a),r.block.d(),r.token=null,r=null}}}function Z(l,n,a){let o;C(l,N,s=>a(2,o=s));let e=[],r=null;const t=()=>R("users/activities","GET").then(s=>s.json()).then(s=>a(0,e=s)),i=s=>{r===s?a(1,r=null):a(1,r=s)};let c=t();return[e,r,o,i,c,s=>i(s.id),s=>o(`/events/${s.id}`),s=>i(s.id)]}class ie extends S{constructor(n){super(),j(this,n,Z,Y,A,{})}}export{ie as default};
